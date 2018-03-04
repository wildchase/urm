package com.panly.urm.prop;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class PropConfig implements EnvironmentAware {

	private static Environment env;
	
	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
	}
	
	public static String getProperty(String key){
		return env.getProperty(key);
	}
	
	public static String getProperty(String key,String defalutValue){
		return env.getProperty(key,defalutValue);
	}
	
	public static <T> T getProperty(String key, Class<T> targetType, T defaultValue){
		return env.getProperty(key, targetType, defaultValue);
	}
	
	public static <T> T getProperty(String key, Class<T> targetType){
		return env.getProperty(key, targetType);
	}
	
	public static String getEnv(){
		String [] profiles = env.getActiveProfiles();
		if(profiles!=null&&profiles.length>0){
			return profiles[0];
		}else{
			return "";
		}
	}
	
	public static boolean isProd(){
		return StringUtils.equals(getEnv(), "");
	}
	
	public static boolean isLocal(){
		return StringUtils.isEmpty(getEnv());
	}
	
	
}
