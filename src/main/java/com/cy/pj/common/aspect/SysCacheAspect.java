package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.cache.DefaultMapCache;

/**
 * 做增加一个缓存:可以减少连接数据库查询的次数，减少数据库压力
 * 做清空缓存:
 * @author Administrator
 *
 */
@Aspect
@Component
public class SysCacheAspect {
	@Autowired
	DefaultMapCache mapCache;
	//切断点
	/**
	 * 做添加一个切入点
	 */
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiedCache)")
	public void doCache() {}
	/**
	 * 做增删改查时清空缓存
	 */
	@Pointcut("@annotation(com.cy.pj.common.annotation.ClearCache)")
	public void doClear() {}
	
	/**
	 * 做一个缓存保存数据的 执行注解标明的方法时，将返回的数据纯存入到缓存中。
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("doCache()")
	public  Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		//获取存放在缓存cache中的数据，也就是ConcurrentHashMap()中的数据。
		Object obj=mapCache.getObject("deptCache");
		if(obj!=null) return obj;    //不为空表示已经有数据，不用到数据库执行查询 直接返回
		Object result = pjp.proceed();  //为空就执行目标方法
		mapCache.putObject("deptCache", result); //把执行的结果存入带缓存中
		return result;
	}
	@AfterReturning("doClear()")
	public void doAfterReturning() {
		mapCache.doClear();
	}
	
}
