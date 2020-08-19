package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;


/**
 * 用户的业务层
 * @author Administrator
 *
 */
public interface SysUserService {
	/**
	 * 修改密码
	 * @param pwd
	 * @param newPwd
	 * @param cfgPwd
	 * @return
	 */
	int updateUserPassword(String pwd,String newPwd,String cfgPwd);
	/**
	 * 查询
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysUserDept> findPageObjects(String username,Integer pageCurrent);
	/**
	 * 对用户的状态做一个修改 点击启用或者禁用1 / 0
	 * @param id
	 * @param valid
	 * @return
	 */
	int validById(Integer id,Integer valid);
	/**
	 * 做添加数据
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	int insertObject(SysUser entity,Integer[] roleIds);
	/**
	 * 做修改时的查询
	 */
	Map<String,Object> findObjectByUserId(Integer id);
	
	int updateObject(SysUser entity,Integer [] roleIds);
	
}
