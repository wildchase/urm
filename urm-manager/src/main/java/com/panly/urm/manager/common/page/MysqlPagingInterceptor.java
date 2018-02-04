package com.panly.urm.manager.common.page;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

import com.panly.urm.manager.common.page.core.PageDTO;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;



@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }))
public class MysqlPagingInterceptor extends AbstractPagingInterceptor {

	
	/**
	 * 改造sql变成查询总数的sql
	 * @param targetSql
	 *            正常查询数据的sql: select id, name from user where name = ?
	 * @return select count(1) from user where name = ?
	 */
	@Override
	protected String getSelectTotalSql(String targetSql) {
		try {
			Statement stmt = CCJSqlParserUtil.parse(targetSql);
			Select select = (Select) stmt;
			SelectBody selectBody = select.getSelectBody();
			if(selectBody instanceof SetOperationList|| selectBody instanceof WithItem){
				throw new RuntimeException("不支持这种类型sql分页："+targetSql);
			}
			PlainSelect plainSelect = (PlainSelect) selectBody;
			List<SelectItem> countItemList = createCountItemList();
			plainSelect.setSelectItems(countItemList);
			List<OrderByElement> emptyOrderByElements = new ArrayList<>();
			plainSelect.setOrderByElements(emptyOrderByElements);
			return plainSelect.toString();
		} catch (JSQLParserException e) {
			throw new RuntimeException("sql错误"+targetSql);
		} catch (Exception e) {
			throw e;
		}

	}
	
	private static List<SelectItem> createCountItemList() {
		ExpressionList expressionList = new ExpressionList();
		List<Expression> expressions = new ArrayList<>();
		expressions.add(new LongValue(1));	
		expressionList.setExpressions(expressions);
		Function count = new Function();
		count.setName("count");
		count.setParameters(expressionList);
		SelectItem countItem = new SelectExpressionItem(count);
		List<SelectItem> newList = new ArrayList<>();
		newList.add(countItem);
		return newList;
	}

	/**
	 * 改造sql变成支持分页的sql
	 * @param targetSql
	 *            正常查询数据的sql: select id, name from user where name = ?
	 * @return select id, name from user where name = ? limit 0,10
	 * 
	 */
	@Override
	protected String getSelectPagingSql(String targetSql,PageDTO<?> pageDto) {
		String sql = targetSql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);
		sqlBuilder.append(" limit ").append(pageDto.getStartRow()).append(",").append(pageDto.getPageSize());
		return sqlBuilder.toString();
	}
	
}