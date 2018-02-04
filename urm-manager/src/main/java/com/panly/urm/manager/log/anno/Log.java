package com.panly.urm.manager.log.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.panly.urm.manager.common.constants.OperTypeEnum;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	
	String value() default "";
	
	OperTypeEnum type() default OperTypeEnum.OPER_DEFAULT;
	
}
