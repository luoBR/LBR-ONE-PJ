package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.sys.entity.SysRole;

@Mapper
public interface SysRoleMenuDao {
	
	/**
	 * 基于菜单id删除角色菜单关系数据
	 * @param id
	 * @return
	 * menu 基于菜单删除
	 */
	@Delete("delete from sys_role_menus where menu_id=#{id}")
	int deleteObjectsByMenuId(Integer id);
	/**
	 * 基于角色id删除菜单和角色关系数据
	 * @param id
	 * @return
	 * role 基于角色删除 
	 */
	@Delete("delete from sys_role_menus where role_id=#{id}")
	int deleteObjectsByRoleId(Integer id);
	
	/**
	 * 保存角色与菜单的数据
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(Integer roleId,Integer[] menuIds);
	/**
	 * 基于角色id查询菜单id
	 * @param id
	 * @return
	 */
	@Select("select menu_id from sys_role_menus where role_id=#{id}")
	List<Integer> findMenuIdsByRoleId(Integer id);
	
	/**基于用户id查找菜单信息id*/
	List<Integer> findMenuIdsByRoleIds(List<Integer> roleIds);
	
}
