package com.panly.urm.common;


import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VelocityUtil {

	private final static Logger logger = LoggerFactory.getLogger(VelocityUtil.class);

	static {
		Properties p = new Properties();
		p.put(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
		try {
			Velocity.init(p);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * @param tmp 模板内容
	 * @param params 变量
	 * @return
	 */
	public static String build(String tmp, Map<String, Object> params) {
		try {
			VelocityContext context = new VelocityContext(params);
			StringWriter stringWriter = new StringWriter();
			Velocity.evaluate(context, stringWriter, "velocityUtil", tmp);
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException("模板生成出错 模板代码【"+tmp+"】   参数：【" + params+"】", e);
		}
	}
	
}
