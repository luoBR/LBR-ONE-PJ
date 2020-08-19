package com.cy.pj.common.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/**
 * 日常信息的记录 抛出异常
 * @author Administrator
 *
 */
@Aspect
@Slf4j
@Component
public class SysLogException {
	
	@Pointcut("bean(sysUserServiceImpl)")
	public void doExceptionPointCut() {}
	//出现异常会可以记录异常信息，但还是要抛出。throwing中 throwing= "e" e要和异常类型一样
	@AfterThrowing(value="doExceptionPointCut()",throwing = "e")
	public void doExceptionLog(JoinPoint jp, Throwable e) {
	 MethodSignature ms=(MethodSignature)jp.getSignature();//连接点对象封装了正在执行的目标方法
		log.error("{} error msg {}",ms.getName(),e.getMessage());
	}
}
