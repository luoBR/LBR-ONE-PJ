package com.cy.pj.common.bo;

import java.io.Serializable;

import lombok.Data;
@Data
public class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7873459177970602861L;
	/**菜单id*/
	private Integer id;
	/**菜单名称*/
	private String name;
	/**上级菜单id*/
	private Integer parentId;
}
