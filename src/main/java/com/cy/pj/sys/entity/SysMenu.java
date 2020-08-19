package com.cy.pj.sys.entity;

import java.util.Date;

import lombok.Data;

/**用来封装客户端提交过来的数据*/
@Data
public class SysMenu {
	private Integer id;
	private String name;
	private String url;
	private Integer type;
	private Integer sort;
	private String note;
	private String permission;
	private Integer parentId;
	private String parentName;
	private String createdUser;
	private String modifiedUser;
	private Date createdTime;
	private Date modifiedTime;
}
