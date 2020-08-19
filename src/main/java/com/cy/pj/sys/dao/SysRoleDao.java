package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.bo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleBO;

@Mapper
public interface SysRoleDao {
	/**统计角色个数:根据name模糊查询*/
	//int getRowCount(String name);
	/**分页查询记录*/
	//List<SysRole> findPageObjects(String name,Integer startIndex,Integer pageSize);
	/**使用PageHelper进行分页查询*/
	List<SysRole> findPageObjects(String name);
	/***
	 * 基于id删除角色菜单关系数据
	 * @param id
	 * @return
	 * 删除角色自身信息 
	 */
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);
	/**
	 * 保存角色自身信息
	 * @param entity
	 * @return
	 */
	/**新增*/
	int insertObject(SysRole entity);
	
	/** 查询  */
	SysRoleBO findObjectById(Integer id);
	/**
	 * 做修改操作
	 * @param sysRole
	 * @return
	 */
	int updateObject(SysRole sysRole);
	/**
	 * 查询角色的id和名称
	 * @return
	 */
	@Select("select id,name from sys_roles ")
	List<CheckBox> findObjects();
}
