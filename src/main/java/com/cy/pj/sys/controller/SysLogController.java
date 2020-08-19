package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@Controller
@RequestMapping("/log/")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 用来对客户端响应日志信息
	 * @param username 用户要查询的名字
	 * @param pageCurrent 输入的页数
	 * @return 返回一个使用Controller封装的JsonResult对象 用来给用户响应数据,响应状态信息,处理的异常
	 * new JsonResult(pageObject),把pageObject封装到JsonResult  封装体现在controller层的业务
	 */
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent){
		PageObject<SysLog> pageObject = sysLogService.findPageObjects(username, pageCurrent);
		return  new JsonResult(pageObject);
	}
	
/* 这个异常处理属于controller这个类的异常处理方法，如果它和全局异常处理类同时存在，如果这个类可以处理这个类型的
 * 异常，那么就会先调用这个类处理异常信息，不让客户端出现500 的异常错误 ，提高用户体验感。
 * @ExceptionHandler这个注解是作为本controller的异常处理 会通过DispatcherServlet调用 如果可以处理异常类型
 * 那么就会调用它处理(value=RuntimeException.class)表示可以处理异常的类型
 * @ExceptionHandler(value=RuntimeException.class)
 * 	@ResponseBody  以json格式的字符串相应给客户端，客户端如果是使用jquery里面的ajax系列方法，那么会自动转为
 * js的json格式的对象 ！！！！但是一般都是定义一个全局异常处理类，处理这个入口包下的所有异常信息。
 * 使用注解@ControllerAdvice 定义一个全局处理异常的类 这样全局都可以使用，提高复用性。
 * public JsonResult doHandleRunTimeException(RuntimeException e)
 * {
 * 
 * return new JsonResult(e); } }
 */
	@RequestMapping("doDeleteIds")
	@ResponseBody
	public JsonResult doDeleteIds(Integer...ids) {
		int deleteSysLog = sysLogService.deleteSysLog(ids);
		return new JsonResult("删除记录数:"+deleteSysLog);
	}
	
	
	
	
	
}