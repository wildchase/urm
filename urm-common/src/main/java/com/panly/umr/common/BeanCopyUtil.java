package com.panly.umr.common;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.beans.BeanCopier;

/**
 * <p>
 * 复制bean 工具类
 * </p>
 *
 * @project core
 * @class CopyUtil.java
 * @author a@panly.me
 * @date 2017年6月1日下午12:43:07
 */
public class BeanCopyUtil {
	
	private final static Logger log =LoggerFactory.getLogger(BeanCopyUtil.class);
	
	/**
	 * 复制 obj 并创建一个新的对象
	 * 若是obj 为空， 则返回null
	 * @param obj
	 * @param clazz
	 * @return  
	 * @exception 不抛出错误
	 */
	public static <T> T copy(Object src, Class<T> clazz) {
		T t = null;
		try {
			if(src!=null){
				t = clazz.newInstance();
				BeanCopier copier = BeanCopier.create(src.getClass(), clazz, false);
		        copier.copy(src, t, null);	
			}
		} catch (Exception e) {
			log.error("copy object error",e);
		}
		return t;
    }
	
	/**
	 * 复制 List<obj> 则返回一个ArrayList <br>
	 * 若是src =null 则返回一个ArrayList size=0 
	 * @param src
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> copyList(List<?> src,Class<T> clazz){
		if(src !=null){
			List<T> list = new ArrayList<T>();
			for (Object obj : src) {
				T t = copy(obj, clazz);
				list.add(t);
			}
			return list;
		}else{
			return new ArrayList<T>();
		}
	}
	
	/**
	 * 将src对象中属性复制到to对象中，相同则覆盖
	 * @param src
	 * @param to
	 */
	public static void copy(Object src, Object to){
		if(src==null || to==null){
			return;
		}else{
			BeanCopier copier = BeanCopier.create(src.getClass(), to.getClass(), false);
			try {
				copier.copy(src, to, null);	
			} catch (Exception e) {
				log.error("copy object error",e);
			}
		}
	}
	
}
