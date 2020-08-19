package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.bo.Node;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
/**
 * 作为菜单管理controller层
 * @author Administrator
 *
 */
//@Controller
//@ResponseBody
//@RestController = @Controller + @ResponseBody(在类上使用表是这个类返回的都是json字符串 
@RestController
@RequestMapping("/menu/")
public class SysMenuController {
	@Autowired
	SysMenuService sysMenuService;
	/**
	 * 查询菜单
	 * @return
	 */
	@RequestMapping("doFindObjects")
	public JsonResult doFindObjects() {
		List<Map<String, Object>> list = sysMenuService.findObject();
		return new JsonResult(list);
	}
	/**
	 * 删除菜单信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id) {
		int deleteObject = sysMenuService.deleteObject(id);
		System.out.println(deleteObject);
		return new JsonResult("delete Ok!");
		
	}
	/**
	 * 做新增时，点击上级菜单显示一个树状图
	 * @return
	 */
	@RequestMapping("doFindZtreeMenuNodes")
	public JsonResult doFindZtreeMenuNodes() {
		List<Node> list = sysMenuService.findZtreeMenuNodes();
		return new JsonResult(list);
	}
	@RequestMapping("doSaveObject")
	public JsonResult doSavaObject(SysMenu sysMenu) {
		sysMenuService.savaObject(sysMenu);
		return new JsonResult("save ok!");
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysMenu sysMenu) {
		sysMenuService.updateObject(sysMenu);
		return new JsonResult("update OK!");
	}
}
