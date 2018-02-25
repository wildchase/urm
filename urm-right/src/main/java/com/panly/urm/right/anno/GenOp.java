package com.panly.urm.right.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为该方法或该类的所有方法  
 * 赋值某一个操作code
 * 只有拥有该权限的id的 用户才能调用该方法，
 * 否则直接抛出错误
 * @author lipan
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GenOp {
	
	String value() ;
	
	String intru() default "";
	
	WorkType type() default WorkType.CHANGE_SQL ;
}
