package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


/**
 * 基于此对象存储表中查询到的数据 
 * 建议在java中所有用于存储数据的对象都实现serializable接口，
 * 将来对象有可能会写到内存的一个缓存中，也有可以通过网络直接去传输。
 * @author Administrator
 *
 */
@Data
public class SysLog implements Serializable {
	/*序列化id的作用:当我们把对象从内存中序列化到磁盘中或者进行网络传输或者从内存中序列到一个cache中
	 * 时把这个对象转为字节时，都会记录一个虚拟化id即便是没有定义id也会自动定义.
	 * 定义的好处:在序列化和反序列化时保证id一直，才能完成序列化和反序列化。
	 * 
	 */
	private static final long serialVersionUID = 8924387722922123121L;
//	private static final long serialVersionUID = 1L; 推荐使用生成较长的id
	
	
	  private Integer id;
      //用户名
      private String username;
      //用户操作
      private String operation;
      //请求方法
      private String method;
      //请求参数
      private String params;
      //执行时长(毫秒)
      
      private Long time;
      //IP地址
      private String ip;
      //创建时间
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
      private Date createdTime;
     


}
