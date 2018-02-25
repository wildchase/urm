package com.panly.urm.right.spring.defined;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.panly.urm.right.aop.GenOpRightAspect;
import com.panly.urm.right.exception.RightException;
import com.panly.urm.right.service.RightService;
import com.panly.urm.right.service.impl.DefaultUserService;
import com.panly.urm.right.service.impl.http.RightHttpServiceImpl;
import com.panly.urm.right.util.RightUtil;

public class AuthBeanDefinitionParser implements BeanDefinitionParser {

	private final static String ASPECT_BEANNAME = com.panly.urm.right.aop.GenOpRightAspect.class.getName();

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		
		// 注入userService Bean
		String userServiceBeanName = element.getAttribute("userService");
		if (userServiceBeanName.equals("default")) {
			userServiceBeanName = "userService";
			parseDefaultUserService(userServiceBeanName, parserContext);
		}
		
		// 注入rightService Bean
		String type = element.getAttribute("type");
		String rightServiceBeanName = "";
		if ("http".equals(type)) {
			rightServiceBeanName = parseHttpRightService(element, parserContext);
		} else if ("cache".equals(type)) {

		} else {
			throw new RuntimeException("auth type 只能是  db,http,cache 三种");
		}
		
		// 注入 GenOpRightAspect bean
		if (!parserContext.getRegistry().containsBeanDefinition(ASPECT_BEANNAME)) {
			RootBeanDefinition aspectBeanDefinition = new RootBeanDefinition(GenOpRightAspect.class);
			aspectBeanDefinition.getPropertyValues().addPropertyValue("userService", new RuntimeBeanReference(userServiceBeanName));
			aspectBeanDefinition.getPropertyValues().addPropertyValue("rightService", new RuntimeBeanReference(rightServiceBeanName));
			parserContext.registerBeanComponent(new BeanComponentDefinition(aspectBeanDefinition, ASPECT_BEANNAME));
		}
		
		//注入RightUtil bean
		if (!parserContext.getRegistry().containsBeanDefinition(RightUtil.class.getName())) {
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RightUtil.class);
			parserContext.registerBeanComponent(new BeanComponentDefinition(beanDefinition, RightUtil.class.getName()));
		}
		
		return null;
	}

	
	private void parseDefaultUserService(String userServiceBeanName, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition(DefaultUserService.class);
		parserContext.getRegistry().registerBeanDefinition(userServiceBeanName, beanDefinition);
	}
	
	private String parseHttpRightService(Element element, ParserContext parserContext) {
		String rightServiceBeanName = RightService.class.getName();
		String address = element.getAttribute("address");
		if(StringUtils.isEmpty(address)){
			throw new RightException("<right:auth type=\"http\" address=? 不能为空" );
		}
		RootBeanDefinition rightHttpBean = new RootBeanDefinition(RightHttpServiceImpl.class);
		rightHttpBean.getPropertyValues().addPropertyValue("baseUrl",address);
		parserContext.getRegistry().registerBeanDefinition(rightServiceBeanName, rightHttpBean);
		return rightServiceBeanName;
	}
	
}
