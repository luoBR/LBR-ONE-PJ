package com.cy.pj.common.bo;

import java.io.Serializable;

import lombok.Data;
/**
 * 借助粗对象封装页面上的<input type=checkbox>对象需要的数据
 * @author Administrator
 *
 */
@Data
public class CheckBox implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5938324948577490244L;
	/**要在添加时提交的表中需要有一个与用户对应的角色id*/
	private Integer id;
	/**在页面上需要显示角色名称*/
	private String name;
}
