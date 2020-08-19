package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
@Service
public class SyslogServiceImpl implements SysLogService {

	@Autowired
	SysLogDao sysLogDao;
	
	
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer  pageCurrent) {
		String tName = Thread.currentThread().getName();
		System.out.println(tName);
		//1.参数检验（增加程序的健壮性) 当前页码不能为null和不能小于1
		//pageCurrent==null||pageCurrent<1 这两者的顺序不能颠倒，如果pageCurrent<1 pageCurrent为空的话
		//或null和1比较会抛出空指针异常 所以在做这种条件时，把为空的放在前面。
		if(pageCurrent==null||pageCurrent<1)
			//抛出一个异常，当前页码值不合法
			throw new IllegalArgumentException("当前页码值不合法");
		
		//2.查询总记录数，并进行校验
		int rowCount = sysLogDao.getRowCount(username);
			/**
			 * 自定义异常：为什么使用自定义异常?使用自定义异常，可以让我们的能清楚的找到是什么位置发出了异常
			 * 方便找到找到异常。不能抛出一个在编译时就会抛出的异常(检测异常如：Exception),
			 *  runtimeException就是可以抛出的异常，但是范围太大。
			 */
		if(rowCount==0)
			throw new ServiceException("没有查询的记录！");
		//3.查询当前页记录
		/*定义每页最多要显示的记录数*/
		int  pageSize = 3 ;
		/*计算当前页查询的起始位置*/
		int startIndex=(pageCurrent-1)*pageSize;//拿到要去查询的第几页 减-1 在乘以每页最大显示数。
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);

		//4.对业务层查询结果进行处理和封装
		//注意！！！在构建对象时传入的参数要和构造方法里面的参数对应。
		return new PageObject<SysLog>(records, rowCount, pageSize,pageCurrent);
	}
	@Override
	/**
	 * 根据选中的id删除日志信息
	 */
	public int deleteSysLog(Integer... ids) {
		//1.先判断是否为空
		if(ids==null||ids.length==0) {
			throw new IllegalArgumentException("请选择需要删除的记录！");
		}
		
		//2.对删除方法出现异常的catch 可能在删除时会出现异常
		int rows;
		try {
			rows = sysLogDao.deleteLogSys(ids);
			
		}catch (Throwable e) {
			e.printStackTrace();//用来告诉维护人员
			throw new ServiceException("出现故障...维修人员在这维修");
		}
		if(rows==0) {
			throw new ServiceException("记录已经被删除！");
		}
		
		return rows;
	}
	//这里的异步写日志操作，同样使用AOP
	//@Async描述的方法为切入点
	//这个切入点上执行的异步操作为通知(advice）
	@Async//由此注解描述的方法，表示告诉spring框架，这个方法是要运行在
	//spring提供的线程池中 可以异步执行。
	@Transactional(propagation = Propagation.REQUIRES_NEW)//表示如果被其他事物条用时，自己开启一个新事物
	@Override
	public void saveObject(SysLog entity) {
		if(entity==null)
			throw new IllegalArgumentException("无效对象");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//获取线程名称
		String tName = Thread.currentThread().getName();
		System.out.println(tName);
		sysLogDao.saveObject(entity);
	}

	
}
