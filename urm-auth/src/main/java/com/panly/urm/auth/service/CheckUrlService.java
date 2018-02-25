package com.panly.urm.auth.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Service
public class CheckUrlService {
	
	private static PathMatcher pathMatcher = new AntPathMatcher();
	
	@Value("${return.url.config}")
	private String returnUrlConfig;
	
	public void checkUrl(String returnUrl){
		String hostName = getHostName(returnUrl);
		boolean matched = isMatch(hostName);
		if(!matched){
			throw new RuntimeException("来源地址不正确");
		}
	}
	
	private boolean isMatch(String hostName) {
		String[] configs = returnUrlConfig.split(",");
		for (String pattern : configs) {
			if(pathMatcher.match(pattern, hostName)){
				return true;
			}
		}
		return false;
	}

	private String getHostName(String returnUrl){
		try {
			URL url = new URL(returnUrl);
			return url.getHost();
		} catch (Exception e) {
			throw new RuntimeException("returnUrl格式不正确");
		}
	}
	
	
}
