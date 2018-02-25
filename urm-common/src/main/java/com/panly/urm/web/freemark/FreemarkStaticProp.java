package com.panly.urm.web.freemark;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.freemarker.static")
public class FreemarkStaticProp {
	
	private Map<String, String> clazzes = new HashMap<String, String>();

	public Map<String, String> getClazzes() {
		return clazzes;
	}

	public void setClazzes(Map<String, String> clazzes) {
		this.clazzes = clazzes;
	}

	
}
