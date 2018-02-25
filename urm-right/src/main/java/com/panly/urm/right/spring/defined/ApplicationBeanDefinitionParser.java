package com.panly.urm.right.spring.defined;

import java.util.Date;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.panly.urm.right.common.CommonUtil;
import com.panly.urm.right.domain.Application;

public class ApplicationBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition(Application.class);
		String id = element.getAttribute("appCode");
		if (id == null) {
			throw new RuntimeException("未配置 appCode");
		}
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		parseProperty(beanDefinition, element);
		beanDefinition.getPropertyValues().addPropertyValue("currentIp", CommonUtil.getCurrentIp());
		beanDefinition.getPropertyValues().addPropertyValue("startTime", new Date());
		return beanDefinition;
	}

	private void parseProperty(RootBeanDefinition beanDefinition, Element element) {
		NamedNodeMap nodes = element.getAttributes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			beanDefinition.getPropertyValues().addPropertyValue(n.getNodeName(), n.getNodeValue());
		}
	}

}
