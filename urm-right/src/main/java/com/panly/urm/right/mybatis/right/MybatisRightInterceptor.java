package com.panly.urm.right.mybatis.right;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.panly.urm.right.exception.RightException;
import com.panly.urm.right.threadlocal.RightThreadLocal;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisRightInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object returnValue = invocation.proceed();
		if (returnValue != null) {
			int changed = (Integer) returnValue;
			if (changed == 0 && RightThreadLocal.get() != null && RightThreadLocal.get().getRightSql() != null
					&& RightThreadLocal.get().getRightSql().size() > 0) {
				String operName = RightThreadLocal.get().getOperName();
				throw new RightException(operName + "操作失败，请查看权限配置");
			}
		}
		return returnValue;

	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
