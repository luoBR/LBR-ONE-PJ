package com.cy.pj.common.aspect;


import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.common.utils.IPUtils;
import com.cy.common.utils.ShiroUtils;
import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

    /**
     * 定义切面对象类型，其特点:
		1)使用@Aspect注解描述
		2)切面内部包含切入点和通知的定义
	 * 2.1)通过Pointcut注解定义切入店(通常会对应某个类或多个类中的方法的集合)
	 * 2.2)通过@Around等注解描述的方法为通知方法(此方法内部要实现扩展业务的织入)
	 * AOP入门 记录方法 执行时长
	 * 
	 *
	 */

@Aspect
@Slf4j
@Component
public class SysLogAspect {
	/**切入点表达式  使用 @Pointcut注解描述*/
	// bean(bean名称)为一个切入点表达式
	//@Pointcut("bean(*ServiceImpl)"):使用* 表示模糊查询的意思，只要时ServiceImpl结尾的都行
	@Pointcut("bean(sysUserServiceImpl)")
	public void logPointcut() {}//做为一个工具
	/**
	 * 环绕通知方法(这个内部可以在目标方法执行之前，之后添加扩展业务逻辑)
	 * @param jp 连接点(封装了切入点中某个正在执行的方法信息)
	 * @return 为目标方法执行结果
	 * @throws Throwable
	 */
	@Around("logPointcut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		try {
			long start = System.currentTimeMillis();
		//log.info("start ："+System.currentTimeMillis()); 这个拼接耗时  语法：start {}",
		log.info("start {}",System.currentTimeMillis());
		Object result = jp.proceed();//调用逻辑--》本类中其他通知-->其他切面-->还有目标方法(要执行业务的方法)
		log.info("end:"+System.currentTimeMillis());
		long end = System.currentTimeMillis();
		/**记录用户的正常行为信息 基于此方法把用户信息写到数据库中*/
		savaLog(jp , end-start);
		return result;
		}catch(Throwable e) {
			log.error("error {}",e.getMessage());//出错的信息
			throw e;
		}
		
	}
	@Autowired
	private SysLogService sysLogService;
	//获取用户行为信息并进行记录
	//谁在什么时间，执行了什么操作，访问了什么方法，传递了什么什么参数，
	private void savaLog(ProceedingJoinPoint jp,long time) throws Exception{
		
		//1.获取用户信息
		/**1.1使用工具类可以获取ip*/
		 String ip=IPUtils.getIpAddr();
		 //1.2通过保存在shiro里面的session获取到用户名
		SysUser user=ShiroUtils.getUserSbuject();
		String username = user.getUsername();
		 
		 /**1.3使用注解获取操作 ：获取方法上RequiredLog注解指定的操作名
		  * 1).获取目标方法对象 通过反射 先获取到字节码对象
		  * 2)获取目标方法对象上的RequirdLog注解
		  * 3).获取注解中指定的操作名
		  * */
		 Class<? extends Object> targetClass = jp.getTarget().getClass();//获取目标对象  类型 的字节码 
		 MethodSignature ms = (MethodSignature)jp.getSignature();//获取到方法签名 方法的信息
		 Method targetMethod = targetClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
		 //获取目标方法上的注解
		 RequiredLog requiredLog = targetMethod.getAnnotation(RequiredLog.class);
		 //获取注解的操作名
		 String operation=null;
		 if(requiredLog!=null) {
			 operation=requiredLog.operation();
		 }
		 //1.4获取目标方法 类名+方法名
		 String method = targetClass.getName()+"."+targetMethod.getName();
		 //1.5获取执行方法传入的参数  jp.getArgs()是一个数组 转换为一个json格式字符串
		 String params=new ObjectMapper().writeValueAsString(jp.getArgs());
		//2.对用户信息进行封装
		SysLog userlog=new SysLog();
		userlog.setIp(ip);
		userlog.setUsername(username);
		userlog.setMethod(method);
		userlog.setParams(params);
		userlog.setOperation(operation);
		userlog.setTime(time);
		//3.保存到数据库  
//		new Thread() { 使用这种匿名内部内的形式实现了一个线程，缺点是
		//频繁的创建线程对象，消耗大量资源  线程复用性不好  可以使用线程池来解决这一问题。
//			public void run() {
//				sysLogService.saveObject(userlog);
//			};
//		}.start();
		sysLogService.saveObject(userlog);
	}
}
