package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;

@Mapper
public interface SysUserDao {
	
	/**
	 * 禁用或启用
	 * @param id
	 * @param valid
	 * @param username
	 * @return
	 */
	@Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	int validById(Integer id,Integer valid,String modifiedUser);
	
	/**
	 * 基于模糊查询查询用户以及用户对应的部门信息
	 * @param username
	 * @return 
	 */
	List<SysUserDept> findPageObjects(String username);
	/**
	 * 做添加操作
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	/**
	 * 做修改的查询
	 * @param id
	 * @return
	 */
	SysUserDept findObjectById(Integer id);
	/**
	 * 做修改操作
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserName(String username);
	
	//根据用户获取到用户的信息，之后根据用户信息拿到密码 把输入的原密码与记录的密码比对
		//如果正确，则可以修改 如果错误 则不能修改密码  校验输入的两次密码是否一致
		//1.基于已经登录的用户信息获取用户对象
		@Update("update sys_users set password=#{pwd},modifiedTime=now(),salt=#{salt} where id=#{userId}")
		int findPasswordByUserIds(Integer userId,String pwd,String salt);
 }
