package com.cy.pj.sys.service;


import java.util.List;

import com.cy.pj.common.bo.CheckBox;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleBO;

/**作为角色管理*/
public interface SysRoleService {
	
		/**
		 * 使用模糊查询以及分页查询角色记录信息
		 * @param name
		 * @param pageCurrent
		 * @return
		 */
	  PageObject<SysRole>  findPageObjects(String name,Integer pageCurrent);
	  /**
	   * 基于角色id删除角色关系数据(角色于菜单的数据，角色于用户的数据) 以及自身信息。
	   * @param id
	   * @return
	   */
	  int deleteObject(Integer id);
	  /**
	   * 保存角色以及角色和菜单的关系数据
	   * @param entity
	   * @param menuIds
	   * @return
	   */
	  int insertObject(SysRole entity,Integer[] menuIds);
	  /**
	   * 做修改操作的查询
	   * @param id
	   * @return
	   */
	  SysRoleBO doFindObjectById(Integer id); 
	  /**
	   * 做修改操作:1,修改角色自身信息。2,根据角色id删除角色于菜单对应的旧关系数据 
	   * 3，插入角色与菜单的新关系数据
	   * @param sysRole
	   * @param menuIds
	   * @return
	   */
	  int doUpdateObject(SysRole sysRole,Integer[] menuIds);
	  /**
	   * 查询角色名称和id
	   * @return
	   */
	  List<CheckBox> findObjects();
}
