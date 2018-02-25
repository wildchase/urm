package com.panly.urm.right.mybatis.right;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panly.urm.common.ReflectUtil;
import com.panly.urm.right.anno.WorkType;
import com.panly.urm.right.threadlocal.RightThreadLocal;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class TemporaryTableInterceptor implements Interceptor {

	private final static Logger logger = LoggerFactory.getLogger(TemporaryTableInterceptor.class);
	
	public final static String TEMPLATE_TABLE="temporary_result";
	
	public final static String TEMPLATE_TABLE_ALIAS="xx";
	
	public final static String TEMPLATE_TABLE_ID="result_id";
	

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 是否有数据授权
		if (RightThreadLocal.get() != null 
				&& RightThreadLocal.get().getWorkType().equals(WorkType.TMEP_SQL)
				&& RightThreadLocal.get().getRightSql() != null
				&& RightThreadLocal.get().getRightSql().size() > 0) {

			Connection conn = (Connection) invocation.getArgs()[0];

			StatementHandler handler = (StatementHandler) invocation.getTarget();
			BoundSql boundSql = handler.getBoundSql();
			MetaObject metaStatementHandler = SystemMetaObject.forObject(handler);
			// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环  
            // 可以分离出最原始的的目标类) 
    		while(metaStatementHandler.hasGetter("h")||metaStatementHandler.hasGetter("target")){
    			if(metaStatementHandler.hasGetter("h")){
    				Object object = metaStatementHandler.getValue("h");  
                    metaStatementHandler = SystemMetaObject.forObject(object);  
    			}
    			if(metaStatementHandler.hasGetter("target")){
    				Object object = metaStatementHandler.getValue("target");  
                    metaStatementHandler = SystemMetaObject.forObject(object);  
    			}
    		}
			MappedStatement ms = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			String commandName = ms.getSqlCommandType().name();
			if (commandName.startsWith("INSERT")) {
				modifyInsertSql(boundSql, conn);
			} else if (commandName.startsWith("UPDATE")) {
				modifyUpdateSql(boundSql, conn);
			} else if (commandName.startsWith("DELETE")) {
				modifyDeleteSql(boundSql, conn);
			} else if (commandName.startsWith("SELECT")) {
				modifySelectSql(boundSql, conn);
			}
			// 执行修改sql 语句
			return invocation.proceed();
		} else {
			// 若是没有
			return invocation.proceed();
		}
	}

	protected void modifySelectSql(BoundSql boundSql, Connection conn) {

		try {
			// 1准备
			prepareRight(conn);

			// 2 获取id字段
			List<String> sqls = RightThreadLocal.get().getRightSql();
			// 获取id字段
			String idName = getIdName(sqls.get(0));
			
			//原始sql 语句
			String sql = boundSql.getSql();
			
			// 3 进行权限拼接后的 select 语句
			String rightSql = selectRight(sql,idName);
			
			logger.info("rightsql:"+rightSql);
			
			// 4 替换执行sql，
			ReflectUtil.forceSetProperty(boundSql, "sql", rightSql);
		} catch (Exception e) {
			throw new RuntimeException("right语句拼接错误",e);
		}
	
	}

	private String selectRight(String sql, String idName) throws Exception {
		Statement stmt = CCJSqlParserUtil.parse(sql);
		Select select = (Select) stmt;
		SelectBody selectBody = select.getSelectBody();
		PlainSelect plainSelect = (PlainSelect) selectBody;
		Table formTable = (Table) plainSelect.getFromItem();
		
		Alias temporaryResultAlias = new Alias(TEMPLATE_TABLE_ALIAS);
		
		String formTableAlias = "";
		if(formTable.getAlias()!=null){
			formTableAlias = formTable.getAlias().getName();
		}else{
			formTableAlias= formTable.getName();
		}
		
		List<Join> joins = plainSelect.getJoins();
		if(joins!=null&&joins.size()>0&&joins.get(0).isSimple()){
			//进入simple模式
			Join rightJoin = new Join();
			rightJoin.setSimple(true);
			Table rightItem = new  Table();
			rightItem.setAlias(temporaryResultAlias);
			rightItem.setName(TEMPLATE_TABLE);
			rightJoin.setRightItem(rightItem);
			joins.add(rightJoin);
			BinaryExpression where = (BinaryExpression)plainSelect.getWhere();
			System.out.println(where);
			EqualsTo onExpression = new EqualsTo();
			Column newleft = new Column(idName);
			Table leftTable = new Table(formTableAlias);
			newleft.setTable(leftTable);
			Column newright = new Column(TEMPLATE_TABLE_ID);
			Table rightTable = new Table(TEMPLATE_TABLE_ALIAS);
			newright.setTable(rightTable);
			onExpression.setLeftExpression(newleft);
			onExpression.setRightExpression(newright);
			AndExpression xxx = new AndExpression(onExpression, where);
			plainSelect.setWhere(xxx);
		}else{
			//进入inner join 模式
			Join rightJoin = new Join();
			rightJoin.setInner(true);
			Table rightItem = new  Table();
			rightItem.setAlias(temporaryResultAlias);
			rightItem.setName(TEMPLATE_TABLE);
			rightJoin.setRightItem(rightItem);
			
			EqualsTo onExpression = new EqualsTo();
			Column left = new Column(idName);
			Table leftTable = new Table(formTableAlias);
			left.setTable(leftTable);
			
			Column right = new Column(TEMPLATE_TABLE_ID);
			Table rightTable = new Table(TEMPLATE_TABLE_ALIAS);
			right.setTable(rightTable);
			
			onExpression.setLeftExpression(left);
			onExpression.setRightExpression(right);
			rightJoin.setOnExpression(onExpression);
			
			if(joins==null){
				joins = new ArrayList<>();
				plainSelect.setJoins(joins);
			}
			joins.add(rightJoin);
		}
		return plainSelect.toString();
	}

	protected void modifyDeleteSql(BoundSql boundSql, Connection conn)throws Throwable {
		//准备数据
		prepareRight(conn);
		
		List<String> sqls = RightThreadLocal.get().getRightSql();
		// 获取id字段
		String idName = getIdName(sqls.get(0));

		String sql = boundSql.getSql();
		StringBuffer rightSql = new StringBuffer("");
		rightSql.append(sql);
		if (StringUtils.containsIgnoreCase(sql, "where")) {
			rightSql.append(" and ");
		}
		rightSql.append(idName).append(" in (select result_id from temporary_result) ");
		logger.info(rightSql.toString());
		ReflectUtil.forceSetProperty(boundSql, "sql", rightSql.toString());
	}

	protected void modifyUpdateSql(BoundSql boundSql, Connection conn)throws Throwable {
		
		// 准备基础数据
		prepareRight(conn);
		
		List<String> sqls = RightThreadLocal.get().getRightSql();
		// 获取id字段
		String idName = getIdName(sqls.get(0));

		String sql = boundSql.getSql();
		StringBuffer rightSql = new StringBuffer("");
		rightSql.append(sql);
		if (StringUtils.containsIgnoreCase(sql, "where")) {
			rightSql.append(" and ");
		}
		rightSql.append(idName).append(" in (select result_id from temporary_result)  ");
		logger.info(rightSql.toString());
		ReflectUtil.forceSetProperty(boundSql, "sql", rightSql.toString());
	}

	protected void modifyInsertSql(BoundSql boundSql, Connection conn)throws Throwable {

		// 准备基础数据
		prepareRight(conn);

		// 原始的语句
		List<String> sqls = RightThreadLocal.get().getRightSql();
		
		// 获取id字段
		String idName = getIdName(sqls.get(0));
		
		// 执行的sql 语句
		String sql = boundSql.getSql();

		//如果 insert中包含select 语句
		if(StringUtils.containsIgnoreCase(sql, "select")){
			StringBuffer rightSql = new StringBuffer(sql);
			
			if(StringUtils.containsIgnoreCase(sql, "where")){
				rightSql.append(" and ");
			}
			rightSql.append(idName).append(" in (select result_id from temporary_result)  ");
			ReflectUtil.forceSetProperty(boundSql, "sql", rightSql.toString());
		}

	}

	protected String getIdName(String sql) {
		try {
			Statement stmt = CCJSqlParserUtil.parse(sql);
			Select select = (Select) stmt;
			SelectBody selectBody = select.getSelectBody();
			PlainSelect plainSelect = (PlainSelect) selectBody;
			List<SelectItem> list = plainSelect.getSelectItems();
			if (list != null & list.size() > 0) {
				SelectExpressionItem item = (SelectExpressionItem) list.get(0);
				Column column = (Column) item.getExpression();
				return column.getColumnName();
			} else {
				throw new RuntimeException("获取id字段错误");
			}
		} catch (JSQLParserException e) {
			throw new RuntimeException("获取id字段错误", e);
		}
	}

	protected void prepareRight(Connection conn) {
		// 1，判断表是否存在，若是没有则创建
		execSql(conn, "create temporary table if not exists temporary_result(result_id bigint)ENGINE=MEMORY");

		// 2，清空数据
		execSql(conn, "TRUNCATE table temporary_result");

		// 3，写入权限数据
		List<String> sqls = RightThreadLocal.get().getRightSql();
		String idName = getIdName(sqls.get(0));

		StringBuffer rightSql = new StringBuffer("");
		rightSql.append("insert into temporary_result(result_id) select " + idName + " as result_id from (");
		// 执行语句
		for (int i = 0; i < sqls.size(); i++) {
			rightSql.append(sqls.get(i));
			if (i != sqls.size() - 1) {
				rightSql.append(" UNION ");
			}
		}
		rightSql.append(") A");
		// 执行语句
		execSql(conn, rightSql.toString());

	}

	private void execSql(Connection conn, String sql) {
		logger.info("临时表操作:" + sql);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.execute();
		} catch (SQLException e1) {
			logger.error("", e1);
			throw new RuntimeException("临时表sql语句执行失败:" + sql, e1);
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		List<String> rightSqls = new ArrayList<>();
		rightSqls.add("select order_id from order_info where order_type in ('1','2')");
		rightSqls.add("select order_id from order_info where order_area_id in ('1','2')");
		
		//获取id 
		Statement rightStmt = CCJSqlParserUtil.parse(rightSqls.get(0));
		Select rightSelect = (Select) rightStmt;
		PlainSelect rightPlainSelect = (PlainSelect) rightSelect.getSelectBody();
		List<SelectItem> rightList = rightPlainSelect.getSelectItems();
		SelectExpressionItem item = (SelectExpressionItem) rightList.get(0);
		
		String idName = ((Column) item.getExpression()).getColumnName();
		System.out.println(idName);
		
		
		
		//select result_id from temporary_result
		
		
		String sql = "select a.order_id,a.order_type,a.order_area_id from order_info a  ";
		
		sql = "select order_id,order_type,order_area_id from order_info ";
		
//		sql="select a.order_id,a.order_type,a.order_area_id from order_info a where a.order_type ='1' and a.order_area_id= '1'";
//		
//		sql="select a.order_id,a.order_type,a.order_area_id from order_info a,user_info b,account c where a.user_id = b.user_id and a.account = c.account and a.order_type ='1'  ";
//		
//		sql="select a.* from order_info a "
//				+ "left join user_info b on a.user_id = b.user_id  "
//				+ " where a.order_type ='1'";
		
		
		Statement stmt = CCJSqlParserUtil.parse(sql);
		Select select = (Select) stmt;
		SelectBody selectBody = select.getSelectBody();
		PlainSelect plainSelect = (PlainSelect) selectBody;
		Table formTable = (Table) plainSelect.getFromItem();
		
		
		
		Alias temporaryResultAlias = new Alias(TEMPLATE_TABLE_ALIAS);
		
		String formTableAlias = "";
		if(formTable.getAlias()!=null){
			formTableAlias = formTable.getAlias().getName();
		}else{
			formTableAlias= formTable.getName();
		}
		
		List<Join> joins = plainSelect.getJoins();
		if(joins!=null&&joins.size()>0&&joins.get(0).isSimple()){
			//进入simple模式
			Join rightJoin = new Join();
			rightJoin.setSimple(true);
			
			Table rightItem = new  Table();
			rightItem.setAlias(temporaryResultAlias);
			rightItem.setName("temporary_result");
			
			rightJoin.setRightItem(rightItem);
			
			joins.add(rightJoin);
			
			
			BinaryExpression where = (BinaryExpression)plainSelect.getWhere();
			System.out.println(where);
			
			EqualsTo onExpression = new EqualsTo();
		
			Column newleft = new Column(idName);
			Table leftTable = new Table(formTableAlias);
			newleft.setTable(leftTable);
			
			Column newright = new Column("result_id");
			Table rightTable = new Table(TEMPLATE_TABLE_ALIAS);
			newright.setTable(rightTable);
			
			onExpression.setLeftExpression(newleft);
			onExpression.setRightExpression(newright);
			
			AndExpression xxx = new AndExpression(onExpression, where);
			
			plainSelect.setWhere(xxx);
			
			System.out.println(plainSelect.toString());
		}else{

			Join rightJoin = new Join();
			rightJoin.setInner(true);
			Table rightItem = new  Table();
			rightItem.setAlias(temporaryResultAlias);
			rightItem.setName("temporary_result");
			rightJoin.setRightItem(rightItem);
			
			EqualsTo onExpression = new EqualsTo();
			Column left = new Column(idName);
			Table leftTable = new Table(formTableAlias);
			left.setTable(leftTable);
			
			Column right = new Column("result_id");
			Table rightTable = new Table(TEMPLATE_TABLE_ALIAS);
			right.setTable(rightTable);
			
			onExpression.setLeftExpression(left);
			onExpression.setRightExpression(right);
			rightJoin.setOnExpression(onExpression);
			
			if(joins==null){
				joins = new ArrayList<>();
				plainSelect.setJoins(joins);
			}
			joins.add(rightJoin);
			System.out.println(plainSelect.toString());
		}
		
	}

}
