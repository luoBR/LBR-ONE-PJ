package com.cy.pj.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO(View Object/Value Object)，在当前项目中我们借助VO封装视图层要呈现的数据
 * 正常数据和非正常数据的状态定义不同
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
public class JsonResult {
	/**表示消息状态码*/
	private Integer state;
	/**消息码对应的具体信息*/
	private String message;
	/**数据(基于此属性封装业务层返回的数据)*/
	private Object data;
	/**
	 * 正常数据的初始化
	 * @param message
	 */
	public JsonResult(String message){
		this.state=1;
		this.message=message;
	}
	/**
	 * 正常数据的初始化
	 * @param data
	 */
	public JsonResult(Object data){
		this.state=1;
		this.data=data;
	}
	/**
	 * 对错误状态的初始化
	 * @param e
	 */
	public JsonResult(Throwable e){//Throwable所有异常的超级父类
		this.state=0;
		this.message=e.getMessage();//获取异常信息
		
		
	}
}
