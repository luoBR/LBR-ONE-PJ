package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.common.bo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.entity.SysUserMenu;

public interface SysMenuService {
	/**
	 * 基于菜单id删除菜单信息以及菜单对应的对应数据。
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	/**  
	 * 查询所有菜单以及菜单对应的上级菜单。
	 * @return 
	 */
	List<Map<String,Object>> findObject();
	
	/**
	 * 做添加时点击上级菜单获取到的树状数据
	 * @return
	 */
	List<Node> findZtreeMenuNodes();
	/**
	 * 添加数据到数据库
	 * @param sysMenu
	 * @return
	 */
	int savaObject(SysMenu sysMenu);
	/**
	 * 修改
	 * @param sysMenu
	 * @return
	 */
	int updateObject(SysMenu sysMenu);
	/**
	 * 根据用户的权限查询到不同的页面
	 * @param id
	 * @return
	 */
	List<SysUserMenu> findUserMenuByUserId(Integer id);
}
