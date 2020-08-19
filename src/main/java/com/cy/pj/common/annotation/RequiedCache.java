package com.cy.pj.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来定义一个自定义注解，做添加缓存的
 * @author Administrator
 *
 */
//注解描述的是方法
@Target(ElementType.METHOD)
//注解的使用时间，运行时使用
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiedCache {
	//表示一个属性 将来可能不同的模块会有不同的cache，基于key先获取cache
	//Map<key,Map>
	String key() default "";
}
