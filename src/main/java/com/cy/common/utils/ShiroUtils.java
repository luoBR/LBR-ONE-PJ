package com.cy.common.utils;

import org.apache.shiro.SecurityUtils;

import com.cy.pj.sys.entity.SysUser;

/**
 * 做Shiro框架的工具类
 * @author Administrator
 *
 */
public class ShiroUtils {
	/**
	 * 获取登录的用户对象
	 * @return
	 */
		public static SysUser getUserSbuject() {
		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
			return user;
		}
		/**
		 * 获取登录用户的名字
		 * @return
		 */
		public static String username() {
		
			return getUserSbuject().getUsername();
		}
}
