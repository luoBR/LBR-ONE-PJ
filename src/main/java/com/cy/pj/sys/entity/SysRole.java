package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4171548607063061289L;
	/**角色id*/
	private Integer id;
	/** 角色名称*/
	private String name;
	/** 角色备注*/
	private String note;
	/**创建时间*/
	private Date createdTime;
	/**创建用户*/
	private String createdUser;
	/**修改时间*/
	private Date modifiedTime;
	/**修改用户*/
	private String modifiedUser;
}
