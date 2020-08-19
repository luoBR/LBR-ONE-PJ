package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysUserRoleDao {
	/**
	 * 基于角色id删除用户和角色关系数据
	 * @param id
	 * @return
	 * 基于角色id删除信息
	 */
	@Delete("delete from sys_user_roles where role_id=#{id}")
	int deleteObjectByRoleId(Integer id);
	/**
	 * 增加用户和角色的对于数据
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int insertObjects(Integer userId,Integer[] roleIds);
	/**
	 * 做修改的查询
	 * @param id
	 * @return
	 */
	@Select("select role_id from sys_user_roles where user_id=#{id}")
	List<Integer> findRoleIdsByUserId(Integer id);
	/**
	 * 做修改的删除，根据用户id删除角色与用户之间的对于关系
	 * @param userId
	 * @return
	 */
	@Delete("delete from sys_user_roles where user_id=#{userId}")
	int deleteByUserId(Integer userId);
	
}
