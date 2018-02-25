package com.panly.urm.right.spring.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.panly.urm.right.spring.defined.ApplicationBeanDefinitionParser;
import com.panly.urm.right.spring.defined.AuthBeanDefinitionParser;
import com.panly.urm.right.spring.defined.LoggerBeanDefinitionParser;

public class RightNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		 registerBeanDefinitionParser("application", new ApplicationBeanDefinitionParser());
		 registerBeanDefinitionParser("auth", new AuthBeanDefinitionParser());
		 registerBeanDefinitionParser("logger", new LoggerBeanDefinitionParser());
	}

}
