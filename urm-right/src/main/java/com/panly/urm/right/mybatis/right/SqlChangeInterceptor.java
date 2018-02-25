package com.panly.urm.right.mybatis.right;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
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
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;

@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class SqlChangeInterceptor implements Interceptor {

	private final static Logger logger = LoggerFactory.getLogger(SqlChangeInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// changeSql 模式
		if (RightThreadLocal.get() != null && RightThreadLocal.get().getWorkType().equals(WorkType.CHANGE_SQL)
				&& RightThreadLocal.get().getRightSql() != null && RightThreadLocal.get().getRightSql().size() > 0) {

			StatementHandler handler = (StatementHandler) invocation.getTarget();
			BoundSql boundSql = handler.getBoundSql();
			MetaObject metaStatementHandler = SystemMetaObject.forObject(handler);
			// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
			// 可以分离出最原始的的目标类)
			while (metaStatementHandler.hasGetter("h") || metaStatementHandler.hasGetter("target")) {
				if (metaStatementHandler.hasGetter("h")) {
					Object object = metaStatementHandler.getValue("h");
					metaStatementHandler = SystemMetaObject.forObject(object);
				}
				if (metaStatementHandler.hasGetter("target")) {
					Object object = metaStatementHandler.getValue("target");
					metaStatementHandler = SystemMetaObject.forObject(object);
				}
			}
			MappedStatement ms = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
			SqlCommandType sqlCommandType= ms.getSqlCommandType();
			
			switch (sqlCommandType) {
			case SELECT:
				modifySelectSql(boundSql);
				break;
			case UPDATE:
				modifyUpdateSql(boundSql);
				break;
			case DELETE:
				modifyDeleteSql(boundSql);
			case UNKNOWN:
			case INSERT:
				break;
			}
			// 执行修改sql 语句
			return invocation.proceed();
		} else {
			return invocation.proceed();
		}
	}

	
	/**
	 * 修改select
	 * 
	 * @param boundSql
	 * @throws Throwable
	 */
	protected void modifySelectSql(BoundSql boundSql) throws Throwable {
		String sql = boundSql.getSql();

		List<String> rightSqls = RightThreadLocal.get().getRightSql();

		Statement stmt = CCJSqlParserUtil.parse(sql);
		Select select = (Select) stmt;
		PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
		
		//获取where
		Expression where = plainSelect.getWhere();
		
		//获取mainTableAlias
		Table mainTable = (Table) plainSelect.getFromItem();
		String mainTableAlias = getMainTableAlias(mainTable);
		
		//where 条件上 加上 rightSql 限制
		where = tranfromWhere(where, mainTableAlias, rightSqls);
		
		//设置
		plainSelect.setWhere(where);
		ReflectUtil.forceSetProperty(boundSql, "sql", plainSelect.toString());
		logger.debug(plainSelect.toString());
	}

	

	/**
	 * xiug delete
	 * 
	 * @param boundSql
	 */
	protected void modifyDeleteSql(BoundSql boundSql) throws Throwable {
		String sql = boundSql.getSql();
		List<String> rightSqls = RightThreadLocal.get().getRightSql();
		Statement stmt = CCJSqlParserUtil.parse(sql);
		Delete delete = (Delete) stmt;
		
		Expression where = delete.getWhere();
		
		String mainTableAlias =  getMainTableAlias(delete.getTable());
		
		where = tranfromWhere(where, mainTableAlias, rightSqls);
		delete.setWhere(where);
		
		ReflectUtil.forceSetProperty(boundSql, "sql", delete.toString());
		logger.debug(delete.toString());
	}

	protected void modifyUpdateSql(BoundSql boundSql) throws Throwable {
		String sql = boundSql.getSql();
		List<String> rightSqls = RightThreadLocal.get().getRightSql();
		Statement stmt = CCJSqlParserUtil.parse(sql);
		Update update = (Update) stmt;
		
		Expression where = update.getWhere();
		
		String mainTableAlias =  getMainTableAlias(update.getTables().get(0));
		
		where = tranfromWhere(where, mainTableAlias, rightSqls);
		update.setWhere(where);
		
		ReflectUtil.forceSetProperty(boundSql, "sql", update.toString());
		logger.debug(update.toString());
	}


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

	private static String getMainTableAlias(Table mainTable){
		String tableAlias = "";
		if (mainTable.getAlias() != null) {
			tableAlias = mainTable.getAlias().getName();
		} 
		return tableAlias;
	}

	private static Expression tranfromWhere(Expression where, String mainTableAlias, List<String> rightSqls) throws JSQLParserException {
		if (rightSqls.size() == 1) {
			Expression rightEx = getRightEx(rightSqls.get(0), mainTableAlias);
			if (where == null) {
				where = rightEx;
			} else {
				where = new AndExpression(where, rightEx);
			}
		} else {
			Expression rightEx0 = getRightEx(rightSqls.get(0), mainTableAlias);
			Expression rightEx1 = getRightEx(rightSqls.get(1), mainTableAlias);
			OrExpression orExpression = new OrExpression(rightEx0, rightEx1);
			for (int i = 2; i < rightSqls.size(); i++) {
				Expression rightEx = getRightEx(rightSqls.get(i), mainTableAlias);
				orExpression = new OrExpression(orExpression, rightEx);
			}
			if (where == null) {
				where = orExpression;
			} else {
				Parenthesis ex = new Parenthesis(orExpression);
				where = new AndExpression(where, ex);
			}
		}
		return where;
	}
	
	/**
	 * 获取right
	 * 
	 * @return
	 * @throws JSQLParserException
	 */
	public static Expression getRightEx(String rightSql, String mainTableAlias) throws JSQLParserException {
		Statement stmtRight = CCJSqlParserUtil.parse(rightSql);
		Select selectRight = (Select) stmtRight;
		PlainSelect plainSelectRight = (PlainSelect) selectRight.getSelectBody();
		Expression rightEx = plainSelectRight.getWhere();
		if (rightEx instanceof InExpression) {
			InExpression in = (InExpression) rightEx;
			Column c = (Column) in.getLeftExpression();
			Table table = new Table(mainTableAlias);
			c.setTable(table);
		} else if (rightEx instanceof EqualsTo) {
			EqualsTo equalTo = (EqualsTo) rightEx;
			Column c = (Column) equalTo.getLeftExpression();
			Table table = new Table(mainTableAlias);
			c.setTable(table);
		}
		return rightEx;
	}

}
