package com.cy.pj.sys.entity;

import lombok.Data;

@Data
public class SysRoleBO {
	/** 角色id*/
	private Integer id;
	/** 角色名称*/
	private String name;
	/** 备注*/
	private String note;
	/**角色id对应的菜单id*/
	private Integer [] menuIds;
}
