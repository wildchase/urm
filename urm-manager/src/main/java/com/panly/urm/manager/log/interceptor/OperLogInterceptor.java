package com.panly.urm.manager.log.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.panly.umr.common.VelocityUtil;
import com.panly.umr.method.MethodParamNameContext;
import com.panly.urm.manager.log.anno.Log;
import com.panly.urm.manager.log.thread.LogThreadContext;
import com.panly.urm.manager.right.entity.UrmOperLog;
import com.panly.urm.manager.user.UserUtil;

public class OperLogInterceptor implements MethodInterceptor {

	private final static Logger logger = LoggerFactory
			.getLogger(OperLogInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		logger.info("log start-------------");
		long start = System.currentTimeMillis();
		
		// 获取注解
		Log log = invocation.getMethod().getAnnotation(Log.class);
		if(log==null){
			return invocation.proceed();
		}
		boolean success = true;
		String errorMsg = "";
		try {
			OperLogContext.init();
			Object obj = invocation.proceed();
			OperLogContext.addParam("ret", obj);
			return obj;
		} catch (Exception e) {
			success = false;
			errorMsg = e.getMessage();
			throw e;
		} finally {
			try {
				// 处理模板基础参数
				initContext(invocation);

				OperLogContext.addParam(OperLogContext.PARAMS_SUCCESS, success);
				OperLogContext.addParam(OperLogContext.PARAMS_ERRORMSG, errorMsg);
				OperLogContext.addParam(OperLogContext.PARAMS_COST, System.currentTimeMillis()-start);

				// 日志写入
				insertOperLog(log);
			} catch (Exception e2) {
				logger.error("日志记录失败",e2);
			}
			logger.info("log end-------------");
			OperLogContext.remove();
		}
	}

	private void insertOperLog(Log log) {
		UrmOperLog operLog = new UrmOperLog();
		
		operLog.setOperType(log.type().getCode());
		String operContent = VelocityUtil.build(log.value(), OperLogContext.getContext());
		operLog.setOperContent(operContent);
		
		operLog.setUserId(OperLogContext.getLong(OperLogContext.PARAMS_USER_ID));
		operLog.setUserName(OperLogContext.getString(OperLogContext.PARAMS_USER_NAME));
		operLog.setUrl(OperLogContext.getString(OperLogContext.PARAMS_URL));
		operLog.setOperCost(OperLogContext.getLong(OperLogContext.PARAMS_COST));
		
		Boolean success =  (Boolean) OperLogContext.get(OperLogContext.PARAMS_SUCCESS);
		if(success){
			operLog.setSuccess(1);
		}else{
			operLog.setSuccess(0);
			operLog.setErrorMsg(OperLogContext.getString(OperLogContext.PARAMS_ERRORMSG));
		}
		operLog.setCreateTime(new Date());
		LogThreadContext.addLog(operLog);
		
	}

	
	/**
	 * 处理基础变量
	 */
	private void initContext(MethodInvocation invocation) {

		Method method = invocation.getMethod();
		// 传入参数名称
		String[] params = MethodParamNameContext.getMethodParamNames(method);
		// 传入参数
		Object[] arguments = invocation.getArguments();
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				OperLogContext.addParam(params[i], arguments[i]);
			}
		}
		OperLogContext.addParam(OperLogContext.PARAMS_USER_NAME, UserUtil.getUserName());
		OperLogContext.addParam(OperLogContext.PARAMS_USER_ID, UserUtil.getUserId());
		
		//获取req path
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String url = request.getRequestURI();
		OperLogContext.addParam(OperLogContext.PARAMS_URL, url);
	}

}
