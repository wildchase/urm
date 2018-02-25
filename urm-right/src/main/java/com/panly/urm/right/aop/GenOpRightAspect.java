package com.panly.urm.right.aop;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.common.IpUtil;
import com.panly.urm.right.anno.GenOp;
import com.panly.urm.right.domain.Application;
import com.panly.urm.right.domain.Right;
import com.panly.urm.right.exception.RightException;
import com.panly.urm.right.service.RightService;
import com.panly.urm.right.service.UserService;
import com.panly.urm.right.threadlocal.RightThreadLocal;

@Aspect
public class GenOpRightAspect implements ApplicationContextAware {

	private final static Logger logger = LoggerFactory.getLogger(GenOpRightAspect.class);

	private static ApplicationContext context;

	private RightService rightService;

	private UserService userService;
	
	private Application app;

	@PostConstruct
	public void init() {
		// 获取 application
		Application application = context.getBean(Application.class);
		if (application == null || application.getAppCode() == null) {
			throw new RightException("请配置 <right:application appCode=?");
		}
		if (userService == null) {
			throw new RightException("请配置userService实现bean <right:auth userService=? ");
		}
		if (rightService == null) {
			throw new RightException("请配置rightService 实现bean type=？ address=？");
		}
		 //校验 application
		 Application query = rightService.checkApplication(application.getAppCode());
		 BeanCopyUtil.copy(query, application);
		 application.setCurrentIp(IpUtil.getIp());
		 logger.info("{}地址 [{}]应用 鉴权功能已经启动",application.getCurrentIp(),application.getAppName());
		 app = application;
	}

	@Pointcut(value = "@annotation(com.panly.urm.right.anno.GenOp)")
	public void genOpAnno() {
	}

	@Before(value = "genOpAnno()")
	private void doBefore(JoinPoint point) {
		
		logger.info("@before：目标方法为：" + point.getSignature().getDeclaringTypeName());
		
		Method method = ((MethodSignature) point.getSignature()).getMethod();

		GenOp genOp = method.getAnnotation(GenOp.class);

		if (genOp == null) {
			throw new RightException("权限注解错误");
		}

		// 1，校验执行权限，当前用户是否有当前方法的权限
		String operCode = genOp.value();

		if (StringUtils.isEmpty(operCode)) {
			throw new RightException("权限注解值不存在");
		}

		// 获取当前登录用户id
		Long acctId = userService.getAcctId();

		// 校验并获取right
		Right right = rightService.checkRight(acctId, operCode,app.getAppCode());

		if (right == null) {
			throw new RightException("权限不存在");
		}

		right.setWorkType(genOp.type());

		// 判断该用户是否有此操作的数据权限，若是有，则按照数据权限执行 切面，若是没有则执行普通操作，在 拦截器中实现
		// 2，写入到线程变量中
		RightThreadLocal.init(right);
	}

	@After(value = "genOpAnno()")
	public void after() {
		// 线程变量中移除
		RightThreadLocal.remove();
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

}
