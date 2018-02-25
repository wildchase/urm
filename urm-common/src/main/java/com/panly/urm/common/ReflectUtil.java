package com.panly.urm.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 反射工具类
 * </p>
 *
 * @project core
 * @class ReflectUtil
 * @author a@panly.me
 * @date 2017年5月27日 下午1:30:32
 */
@SuppressWarnings("all")
public class ReflectUtil {

	private static Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制 forceGetProperty
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Object forceGetProperty(Object object, String propertyName)  {
		if(object == null){
			return null;
		}
		if(object instanceof Map){
			Map<String, Object> map = (Map<String, Object>) object; 
			return map.get(propertyName);
		}
		
		Field field = getDeclaredField(object, propertyName);
		if(field==null){
			logger.debug(object+"中没有{}属性",propertyName);
			return null;
		}
		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {

		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制. forceSetProperty
	 * 
	 * @param object
	 * @param propertyName
	 * @param newValue
	 * @throws NoSuchFieldException
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		if(object == null){
			return;
		}
		if(object instanceof Map){
			Map<String, Object> map = (Map<String, Object>) object; 
			map.put(propertyName, newValue);
			return;
		}
		Field field = getDeclaredField(object, propertyName);
		if(field==null){
			logger.debug(object+"中没有{}属性",propertyName);
			return;
		}
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
		}
		field.setAccessible(accessible);
	}

	/**
	 * 获取属性对象
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getDeclaredField(Object object, String propertyName) {
		return getDeclaredField(object.getClass(), propertyName);
	}

	private static Field getDeclaredField(Class clazz, String propertyName) {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 调用当前类声明的private/protected函数 invokePrivateMethod
	 * 
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object[] params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}

	/**
	 * 调用当前类声明的private/protected函数 invokePrivateMethod
	 * 
	 * @param object
	 * @param methodName
	 * @param param
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object param)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokePrivateMethod(object, methodName, new Object[] { param });
	}

	/**
	 * toString
	 * 
	 * @param obj
	 * @return
	 */
	public static String fieldsToString(Object obj) {
		int levels = 0;
		Class sourceClass = obj.getClass();
		while (!sourceClass.getName().equals("java.lang.Object")) {
			levels++;
			sourceClass = sourceClass.getSuperclass();
		}
		levels--;
		return p_fieldsToString(obj, levels);
	}

	private static String p_fieldsToString(Object obj, int superLevels) {
		String[] results = new String[superLevels + 1];
		StringBuffer result = new StringBuffer();
		try {
			Class sourceClass = obj.getClass();

			for (int level = 0; level <= superLevels; level++) {
				Field[] fields = sourceClass.getDeclaredFields();

				for (int i = 0; i < fields.length; i++) {
					Field f = fields[i];
					int modifier = f.getModifiers();
					if (Modifier.isStatic(modifier)) {
						continue;
					}
					f.setAccessible(true);
					String name = f.getName();
					if (name.indexOf("m_") == 0) {
						name = name.substring(2);
					}
					Object tempObject = f.get(obj);
					result.append(name);
					result.append("[");
					result.append(tempObject);
					result.append("]");
					if (i < fields.length - 1) {
						result.append(",");
					}
					result.append(" ");
				}
				results[superLevels - level] = result.toString();
				result.setLength(0);
				sourceClass = sourceClass.getSuperclass();
			}

			for (int i = 0; i <= superLevels; i++) {
				result.append(results[i]);
			}
		} catch (Exception ex) {
			logger.error("ReflectUtil: Problem reflecting fields", ex);
		}
		return result.toString();
	}
}
