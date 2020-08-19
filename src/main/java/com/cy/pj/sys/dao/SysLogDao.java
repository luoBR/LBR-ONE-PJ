package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;

@Mapper
public interface SysLogDao {
	
	int saveObject(SysLog entity);
	
	
	/** 使用文档注释,对于这样的内容，要提举到一个API文档中的话，可以通过javadoc指令提取
	 * 方法上面 属性上面的文档注释提取出来，生成一个api文档。
	 * 基于条统计记录总数  
	 * @param username 查询条件 用户名
	 * @return 查询到的记录总数
	 */
	int getRowCount(String username);
	
	/**
	 * 基于条件查询当前页的记录
	 * @param username 查询条件
	 * @param startIndex 当前页起始位置
	 * @param pageSize 页面大小(指定最多显示多少条记录)
	 * @return 查询到的当前日志信息
	 */
	List<SysLog> findPageObjects(String username,Integer startIndex,Integer pageSize);
	/**
	 * 基于多个id删除日志信息
	 * @param ids 日志记录id 假如没有使用这个注解@Param指定数组名称，在映射文件中被遍历的数组名称。
	 * @return 
	 */
	int deleteLogSys(@Param("ids")Integer...ids);
}
