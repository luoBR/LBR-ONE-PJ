package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 用来接收提交的用户信息
 * @author Administrator
 *
 */
@Data
public class SysUser implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -5753947297349456427L;
	private Integer id;
     private String username;
     private String password;
     private String salt;//盐值
     private String email;
     private String mobile;
     private Integer valid=1;//状态码 0/1
     private Integer deptId;
     private Date createdTime;
     private Date modifiedTime;
     private String createdUser;
     private String modifiedUser;
}
