package com.fangdd.testcenter.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 解析json请求格式为javaBean注解
 *
 * @since 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonToBean {

	/**
	 * json转bean时，目标对象是否可以为null
	 * 
	 * @return
	 */
	boolean nullable() default false;
}
