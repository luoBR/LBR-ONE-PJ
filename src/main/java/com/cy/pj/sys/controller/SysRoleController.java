package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleBO;
import com.cy.pj.sys.service.SysRoleService;

@RestController
@RequestMapping("/role/")
public class SysRoleController {
	@Autowired
	SysRoleService sysRoleService;
	/**
	 * 模糊查询以及分页查询 使用pageHelper
	 * @param name
	 * @param pageCurrent
	 * @return
	 */
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindObjects(String name ,Integer pageCurrent) {
		PageObject<SysRole> data = sysRoleService.findPageObjects(name, pageCurrent);
		return new JsonResult(data);
	}
	/**
	 * 做删除角色信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete Ok!");
	}
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysRole entity,Integer[] menuIds) {
		sysRoleService.insertObject(entity, menuIds);
		return new JsonResult("save Ok!");
	}
	@RequestMapping("doFindObjectById")
	public JsonResult doUpdateObject(Integer id) {
		SysRoleBO objectById = sysRoleService.doFindObjectById(id);
		return new JsonResult(objectById);
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysRole sysRole,Integer[] menuIds) {
		sysRoleService.doUpdateObject(sysRole, menuIds);
		return new JsonResult("update ok");
	}
	@RequestMapping("doFindRoles")
	public JsonResult doFindRoles() {
		return new JsonResult(sysRoleService.findObjects());
	}
}
