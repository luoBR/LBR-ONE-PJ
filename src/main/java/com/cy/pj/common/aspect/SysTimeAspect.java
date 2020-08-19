package com.cy.pj.common.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class SysTimeAspect {
	
	//@Pointcut("bean(sysUserServiceImpl)")
	public void testRunTime() {}
	/**
	 * 目标方法执行之前
	 * @param j
	 */
	//@Before("testRunTime()")
	public void before(JoinPoint j) {//2
		System.out.println("before");
	}
	
	/**
	 * 目标方法执行之后 无论前面是否有异常都会执行。
	 * @param j
	 */
	//@After("testRunTime()")
	public void after(JoinPoint j) {
		System.out.println("after");
	}
	/**
	 * After执行完正常返回
	 * @param j
	 */
	//@AfterReturning("testRunTime()")
	public void afterReturning(JoinPoint j){
		System.out.println("afterReturning");//
	}
	/**
	 * After执行有异常
	 * @param j
	 */
	//@AfterThrowing("testRunTime()")
	public void afterThrowint(JoinPoint j) {
		System.err.println("afterThrowing");
	}
	//@Around("testRunTime()")
	public Object around(ProceedingJoinPoint js) throws Throwable {
		System.err.println("around.Before");//1
		 Object proceed = js.proceed();
		System.err.println("around.After");//4
		return proceed;
	}
	
	
}
