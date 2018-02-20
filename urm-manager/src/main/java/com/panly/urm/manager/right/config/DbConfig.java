package com.panly.urm.manager.right.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panly.urm.manager.common.constants.RecordStatusEnum;
import com.panly.urm.manager.right.dao.UrmRightDbConfigDao;
import com.panly.urm.manager.right.dao.UrmRightValueSetConfigDao;
import com.panly.urm.manager.right.entity.UrmRightDbConfig;
import com.panly.urm.manager.right.entity.UrmRightValueSetConfig;
import com.panly.urm.manager.right.vo.DataRightExecResult;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author lipan
 */
@Component
public class DbConfig {

	private final static Logger logger = LoggerFactory
			.getLogger(DbConfig.class);

	@Autowired
	private UrmRightDbConfigDao urmRightDbConfigDao;

	@Autowired
	private UrmRightValueSetConfigDao urmRightValueSetConfigDao;

	private Map<String, DataSource> dbs = new ConcurrentHashMap<>();

	private List<UrmRightDbConfig> dbConfigs = null;

	private List<UrmRightValueSetConfig> valueSetConfigs = null;

	/**
	 * 获取db 配置
	 * 
	 * @return
	 */
	public List<UrmRightDbConfig> getRightDbConfigs() {
		if (dbConfigs == null) {
			UrmRightDbConfig record = new UrmRightDbConfig();
			record.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
			dbConfigs = urmRightDbConfigDao.find(record);
		}
		return dbConfigs;
	}
	
	
	/**
	 * 获取value 配置
	 * 
	 * @return
	 */
	public List<UrmRightValueSetConfig> getRightValueSetConfigs() {
		if (valueSetConfigs == null) {
			UrmRightValueSetConfig record = new UrmRightValueSetConfig();
			record.setRecordStatus(RecordStatusEnum.NORMAL.getCode());
			valueSetConfigs = urmRightValueSetConfigDao.find(record);
		}
		return valueSetConfigs;
	}

	/**
	 * 获取db单个配置
	 * 
	 * @param dbCode
	 * @return
	 */
	public UrmRightDbConfig getRightDbConfig(String dbCode) {
		if (dbConfigs == null) {
			getRightDbConfigs();
		}
		for (int i = 0; i < dbConfigs.size(); i++) {
			UrmRightDbConfig c = dbConfigs.get(i);
			if (StringUtils.equals(dbCode, c.getDbCode())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * 获取db单个配置 名称
	 * 
	 * @param dbCode
	 * @return
	 */
	public String getRightDbConfigName(String dbCode) {
		UrmRightDbConfig config = getRightDbConfig(dbCode);
		if (config != null) {
			return config.getDbName();
		} else {
			return "";
		}
	}

	/**
	 * 获取value单个配置
	 * 
	 * @param valueCode
	 * @return
	 */
	public UrmRightValueSetConfig getValueSetConfig(String valueCode) {
		if (valueSetConfigs == null) {
			getRightValueSetConfigs();
		}
		for (int i = 0; i < valueSetConfigs.size(); i++) {
			UrmRightValueSetConfig c = valueSetConfigs.get(i);
			if (StringUtils.equals(valueCode, c.getValueCode())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * 获取value单个配置 名称
	 * 
	 * @param valueCode
	 * @return
	 */
	public String getValueSetConfigName(String valueCode) {
		UrmRightValueSetConfig config = getValueSetConfig(valueCode);
		if (config != null) {
			return config.getValueName();
		} else {
			return "";
		}
	}

	public String tranValueConfigToSql(String valueCode, String valueConfig) {
		UrmRightValueSetConfig valueConfigSet = getValueSetConfig(valueCode);
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(valueConfigSet.getValueTableKey());
		sql.append(" from ").append(valueConfigSet.getValueTableName());
		sql.append(" where ").append(valueConfigSet.getValueTableColumn());

		List<String> keys = splitValueConfig(valueConfig);
		if (keys.size() == 1) {
			sql.append(" = ");
			if (isVarcharType(valueConfigSet)) {
				sql.append("'");
			}
			sql.append(keys.get(0));
			if (isVarcharType(valueConfigSet)) {
				sql.append("'");
			}
		} else {
			sql.append(" in (");
			for (int i = 0; i < keys.size(); i++) {
				if (isVarcharType(valueConfigSet)) {
					sql.append("'");
				}
				sql.append(keys.get(i));
				if (isVarcharType(valueConfigSet)) {
					sql.append("'");
				}
				if (i != keys.size() - 1) {
					sql.append(",");
				}
			}
			sql.append(" )");
		}

		return sql.toString();
	}

	private boolean isVarcharType(UrmRightValueSetConfig valueConfigSet) {
		String valueColumnType = valueConfigSet.getValueColumnType();
		if (StringUtils.equals(valueColumnType, "1")) {
			return true;
		}
		return false;
	}

	private List<String> splitValueConfig(String valueConfig) {
		List<String> list = new ArrayList<>();
		String[] a = valueConfig.split(",");
		for (String s : a) {
			String[] x = s.split("#");
			for (String key : x) {
				list.add(key);
			}
		}
		return list;
	}
	
	public String getValueConfigName(String valueCode, String valueConfig){
		logger.info(valueCode);
		UrmRightValueSetConfig config =getValueSetConfig(valueCode);
		if(config ==null){
			return "";
		}
		Map<String, String> map =  tranToMap(config.getValueConfig());
		List<String> keys = tranToList(valueConfig);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			sb.append(map.get(key));
			if(i!=keys.size()-1){
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private List<String> tranToList(String valueConfig) {
		List<String> list = new ArrayList<>();
		String[] values = valueConfig.split(",");
		for (String v : values) {
			list.add(v);
		}
		return list;
	}


	private Map<String, String> tranToMap(String valueConfig) {
		Map<String, String> map = new HashMap<String, String>();
		String[] values = valueConfig.split(",");
		for (String v : values) {
			String[] kv = v.split(":");
			if(kv.length==2){
				map.put(kv[1], kv[0]);
			}
		}
		return map;
	}


	public Connection getConnection(String dbCode) {
		try {
			if (dbs.containsKey(dbCode)) {
				DataSource dataSource = dbs.get(dbCode);
				return dataSource.getConnection();
			} else {
				UrmRightDbConfig config = urmRightDbConfigDao
						.getByPrimaryKey(dbCode);
				if (config == null) {
					throw new RuntimeException("不存在该数据链路");
				}
				HikariConfig configuration = new HikariConfig();
				configuration.setDriverClassName(config.getDbClass());
				configuration.setJdbcUrl(config.getDbUrl());
				configuration.setUsername(config.getDbUsername());
				configuration.setPassword(config.getDbPassword());
				configuration.setReadOnly(true);
				DataSource dataSource = new HikariDataSource(configuration);
				dbs.put(dbCode, dataSource);
				return dbs.get(dbCode).getConnection();
			}
		} catch (Exception e) {
			throw new RuntimeException("获取数据链路连接错误", e);
		}
	}

	public void checkSelectSql(String sql, Connection conn) {
		try {
			Statement stmt = CCJSqlParserUtil.parse(sql);
			Select select = (Select) stmt;
			SelectBody selectBody = select.getSelectBody();
			if (!(selectBody instanceof PlainSelect)) {
				throw new RuntimeException("不是简单查询语句");
			}
			PlainSelect plainSelect = (PlainSelect) selectBody;
			List<SelectItem> items = plainSelect.getSelectItems();
			if (items.size() != 1) {
				throw new RuntimeException("查询语句的column只能是一个");
			}
			SelectItem item = items.get(0);
			if (!(item instanceof SelectExpressionItem)) {
				throw new RuntimeException("查询column不正确");
			}
			SelectExpressionItem selectExpressionItem = (SelectExpressionItem) item;
			if (!(selectExpressionItem.getExpression() instanceof Column)) {
				throw new RuntimeException("查询column不正确");
			}
			Column column = (Column) selectExpressionItem.getExpression();
			String columnName = column.getColumnName();
			logger.info(columnName);
		} catch (JSQLParserException e) {
			throw new RuntimeException("sql语句格式不正确");
		}
	}

	public DataRightExecResult exec(Connection conn, String sql)
			throws SQLException {
		DataRightExecResult result = execListMap(conn, sql);
		int count = execCount(conn, sql);
		result.setCount(count);
		return result;
	}

	private int execCount(Connection conn, String sql) throws SQLException {
		sql = getCountSql(sql);
		logger.info("exec {}", sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				return totalRecord;
			} else {
				return 0;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	private DataRightExecResult execListMap(Connection conn, String sql)
			throws SQLException {
		sql = getLimitSql(sql);
		logger.info("exec {}", sql);
		DataRightExecResult result = new DataRightExecResult();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<String> columns = getColumns(rs);
			result.setColumns(columns);
			result.setResult(resultSetToArray(rs, columns));
			return result;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	private List<String> getColumns(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<String> colNameList = new ArrayList<String>();
		for (int i = 0; i < colCount; i++) {
			colNameList.add(rsmd.getColumnName(i + 1));
		}
		return colNameList;
	}

	private List<Object[]> resultSetToArray(ResultSet rs,
			List<String> colNameList) throws SQLException {
		List<Object[]> results = new ArrayList<Object[]>();
		while (rs.next()) {
			Object[] objs = new Object[colNameList.size()];
			for (int i = 0; i < colNameList.size(); i++) {
				Object value = rs.getString(colNameList.get(i));
				objs[i] = value;
				results.add(objs);
			}
		}
		return results;
	}

	private String getCountSql(String sql) {
		try {
			Statement stmt = CCJSqlParserUtil.parse(sql);
			Select select = (Select) stmt;
			SelectBody selectBody = select.getSelectBody();
			if (!(selectBody instanceof PlainSelect)) {
				throw new RuntimeException("不支持这种类型sql：" + sql);
			}
			PlainSelect plainSelect = (PlainSelect) selectBody;
			List<SelectItem> countItemList = createCountItemList();
			plainSelect.setSelectItems(countItemList);
			List<OrderByElement> emptyOrderByElements = new ArrayList<>();
			plainSelect.setOrderByElements(emptyOrderByElements);
			return plainSelect.toString();
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}

	private String getLimitSql(String sql) {
		sql = sql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);
		sqlBuilder.append(" limit ").append(0).append(",").append(10);
		return sqlBuilder.toString();
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

	public void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
