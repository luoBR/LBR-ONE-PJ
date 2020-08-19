package com.cy.pj.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;
import com.cy.pj.sys.service.SysUserService;

@RestController
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doUpdatePassword")
	public JsonResult doPwdUpdate(String pwd,String newPwd,String cfgPwd) {
		sysUserService.updateUserPassword(pwd, newPwd, cfgPwd);
		return new JsonResult("updatepassword ok!");
	}
	@RequestMapping("doLogin")
	public JsonResult doLogin(String username,String password,boolean isRememberMe) {
		//把输入的用户名和密码封装 UsernamePasswordToken 对象
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//设置记住我的功能
		token.setRememberMe(isRememberMe);
		// 获取subject对象 用于提交
		Subject subject = SecurityUtils.getSubject();
		// 将输入的用户信息提交到securityManager中验证
		subject.login(token);
		return new JsonResult("login ok");
	}
	
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysUserDept> data = sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(data);
	}
	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id,Integer valid) {
		sysUserService.validById(id, valid);
		return new JsonResult("update ok!");
	}
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds) {
		sysUserService.insertObject(entity, roleIds);
		return new JsonResult("save ok!");
	}
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectByUserId(Integer id) {
		return new JsonResult(sysUserService.findObjectByUserId(id));
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysUser entity,Integer [] roleIds) {
		sysUserService.updateObject(entity, roleIds);
		return new JsonResult("update ok!");
	}
}
