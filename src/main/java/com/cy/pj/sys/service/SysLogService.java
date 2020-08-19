package com.cy.pj.sys.service;


import com.cy.pj.common.bo.PageObject;
import com.cy.pj.sys.entity.SysLog;
/**
 * 业务层接口 ：封装对业务逻辑的实现 负责定义日志业务模块的规则
 * 1)添加日志业务(AOP)
 * 2)查询日志业务(添加分页业务实现）
 * 3)删除日志业务(进行权限控制）
 * @author Administrator
 *
 */
public interface SysLogService {//SysLogServiceImpl为实现类
	/**
	 * 定义日志的分页查询业务
	 * @param username 用户名(数据最终来源为client 用户端)
	 * @param pageCurrent 当前页码值(client 用户端)
	 * @return 封装当前野记录和分页信息的对象(PageObject
	 */
	 PageObject<SysLog> findPageObjects(String username,Integer pageCurrent);
	 /**
	  * 基于id删除
	  * @param ids
	  * @return
	  */
	 int deleteSysLog(Integer...ids);
	 /**
	  * 做添加日志操作
	  * @param entity
	  */
	 void  saveObject(SysLog entity);
}
