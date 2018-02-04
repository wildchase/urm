package com.panly.urm.manager.log.interceptor;


import java.util.HashMap;
import java.util.Map;

public class OperLogContext {
	
	public final static String PARAMS_USER_ID ="userId";
	
	public final static String PARAMS_USER_NAME ="userName";
	
	public final static String PARAMS_SUCCESS ="success";
	public final static String PARAMS_ERRORMSG ="errorMsg";
	
	public final static String PARAMS_URL ="url";
	
	public final static String PARAMS_COST ="cost";
	
	private static ThreadLocal<Map<String, Object>> contextThreadLocal= new  ThreadLocal<Map<String, Object>>();
	
	static void init(){
		Map<String, Object> context = new HashMap<>();
		contextThreadLocal.set(context);
	}
	
	static Map<String, Object> getContext(){
		return contextThreadLocal.get();
	}
	
	static void remove(){
		contextThreadLocal.remove();
	}
	
	public static void addParam(String key,Object value){
		if(contextThreadLocal.get()!=null){
			contextThreadLocal.get().put(key, value);
		}
	}
	
	
	public static Long getLong(String key) {
		Object o = get(key);
		if(o==null){
			return null;
		}
		if(o instanceof Long){
			return (Long) o;
		}
		if(o instanceof String){
			return Long.parseLong((String) o);
		}
		return null;
	}

	public static String getString(String key) {
		Object o = get(key);
		if(o==null){
			return null;
		}
		if(o instanceof String){
			return (String) o;
		}else{
			return o.toString();
		}
	}
	
	public static Object get(String key) {
		if(contextThreadLocal.get()!=null){
			return contextThreadLocal.get().get(key);
		}else{
			return null;
		}
	}
	
	


}
