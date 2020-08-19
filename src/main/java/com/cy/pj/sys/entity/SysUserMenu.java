package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 用来封装每个用户登录后看到不同的页面
 * @author Administrator
 *
 */
@Data
public class SysUserMenu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4969887349534209298L;
	/**一级菜单*/
	private Integer id;
	private String name;
	private String url;
	/**二级菜单*/
	private List<SysUserMenu> childs;
}
