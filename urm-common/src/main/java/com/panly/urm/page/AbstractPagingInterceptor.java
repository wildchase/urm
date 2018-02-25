package com.panly.urm.page;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.panly.urm.page.core.PageDTO;
import com.panly.urm.page.core.PageThreadLocal;




public abstract class AbstractPagingInterceptor implements Interceptor {

	private static final Logger logger =LoggerFactory.getLogger(AbstractPagingInterceptor.class);
	
    private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");

    private static final String FIELD_SQL = "sql";
    
    private static final String BLANK = " ";

    public static final String SELECT = "select";
    
    public static final String FROM = "from";
    
    public static final String ORDER_BY = "order by";
    
    public static final String UNION = "union";
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    	//进入分页模式，
    	if(PageThreadLocal.get()!=null){
    		
    		PageDTO<?> pageDTO = PageThreadLocal.get();
    		
    		Connection connection = (Connection) invocation.getArgs()[0];
    		
    		StatementHandler handler = (StatementHandler) invocation.getTarget();
    		
    		MetaObject metaStatementHandler = SystemMetaObject.forObject(handler);  
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环  
            // 可以分离出最原始的的目标类)  
            while (metaStatementHandler.hasGetter("h")) {  
                Object object = metaStatementHandler.getValue("h");  
                metaStatementHandler = SystemMetaObject.forObject(object);  
            }  
            // 分离最后一个代理对象的目标类  
            while (metaStatementHandler.hasGetter("target")) {  
                Object object = metaStatementHandler.getValue("target");  
                metaStatementHandler = SystemMetaObject.forObject(object);  
            }  
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");  

            BoundSql boundSql = handler.getBoundSql();

            //replace all blank
            String targetSql = replaceSqlBlank(boundSql.getSql());
            
            //paging
            getTotalAndSetInPagingBounds(targetSql, boundSql, pageDTO, mappedStatement, connection);
            
            String pagingSql = getSelectPagingSql(targetSql, pageDTO);
            
            logger.info(pagingSql);
            
            writeDeclaredField(boundSql, FIELD_SQL, pagingSql);
            
            return invocation.proceed();
    	}else{
    		//直接忽略
    		return invocation.proceed();
    	}
    }

    private void getTotalAndSetInPagingBounds(String targetSql, BoundSql boundSql, PageDTO<?> pageDTO, 
                            MappedStatement mappedStatement, Connection connection) throws SQLException {
        String totalSql = getSelectTotalSql(targetSql);
        logger.info(totalSql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(), totalSql, parameterMappings, parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, totalBoundSql);
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(totalSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                int totalRecord = rs.getInt(1);
                pageDTO.setTotal(totalRecord);
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
        }
    }

    protected abstract String getSelectTotalSql(String targetSql);
    
    protected abstract String getSelectPagingSql(String targetSql, PageDTO<?> pageDTO);

    private String replaceSqlBlank(String originalSql) {
        Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
        return matcher.replaceAll(BLANK);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    private void writeDeclaredField(Object target, String fieldName, Object value) 
            throws IllegalAccessException {
        if (target == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        Class<?> cls = target.getClass();
        Field field = getField(cls, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
        }
        field.set(target, value);
    }

    private static Field getField(final Class<?> cls, String fieldName) {
        for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
            try {
                Field field = acls.getDeclaredField(fieldName);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                    return field;
                }
            } catch (NoSuchFieldException ex) {
                // ignore
            }
        }
        return null;
    }
    
    @Override
    public void setProperties(Properties properties) {

    }
}