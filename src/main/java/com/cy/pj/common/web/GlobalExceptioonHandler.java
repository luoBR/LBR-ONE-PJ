package com.cy.pj.common.web;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
/**
 * 作为全局异常处理类，使用注解@ControllerAdvice 描述类
 * 使用@ExceptionHandler描述处理异常的类型 之后调用vo封装的返回异常的
 * 构造方法返回异常信息给客户端，这样可以增加用户体验。
 */
import org.springframework.web.bind.annotation.ControllerAdvice;
/**
 * 全局异常处理类 @ControllerAdvice：定义全局异常处理类必须使用这个注解。
 * 1)假如XxxController类中出现了异常，此异常没有被捕获，而是继续抛出，
 * 这个异常会抛给方法的controller方法的调用者(DispatcherServlet),此对象会检测抛出异常的controller类中
 * 是否有定义异常的处理方法，这个方法能否处理抛出异常。假如不可以，那么DispatcherServlet对象还会检测是否有全局的异常
 * 处理类，假如有则调用全局 异常处理类中的相关异常处理方法。在全局异常处理类中没有定义类型的异常，会抛给客户端
 * 这样就会报500 错误等等。。。
 * @author Administrator
 *
 */
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
@ControllerAdvice
public class GlobalExceptioonHandler {
	  @ExceptionHandler(ShiroException.class)
	  @ResponseBody
      public JsonResult doHandleShiroException(
                      ShiroException e) {
              JsonResult r=new JsonResult();
              r.setState(0);
              if(e instanceof UnknownAccountException) {
                      r.setMessage("账户不存在");
              }else if(e instanceof LockedAccountException) {
                      r.setMessage("账户已被禁用");
              }else if(e instanceof IncorrectCredentialsException) {
                      r.setMessage("密码不正确");
              }else if(e instanceof AuthorizationException) {
                      r.setMessage("没有此操作权限");
              }else {
                      r.setMessage("系统维护中");
              }
              e.printStackTrace();
              return r;
      }
		@ExceptionHandler(value=RuntimeException.class)
		@ResponseBody
		public JsonResult doHandleRunTimeException(RuntimeException e) {
			e.printStackTrace();
			return new JsonResult(e);//这个封装的类中调用e.getMessage()方法获取我们抛出的字符。
		}
}
