package com.cy.py.sys.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@SpringBootTest
public class SysLogServiceTest {
	@Autowired
	SysLogService sysLogService;
	@Autowired
	SysLogDao sysLogDao;
	@Autowired
	SysMenuDao sysMenuDao;
	
	@Test
	public void testService() {
	   PageObject<SysLog> findPageObjects = sysLogService.findPageObjects("adminlte",0);
	   System.out.println(findPageObjects);
	}
	@Test
	public void testJsonResult() {
		String me="methid";
		System.out.println(new JsonResult(me));
		
	}
	@Test
	public void testDeleteSysLog() {
		Integer[] ids = {50,55};
		int logSys = sysLogDao.deleteLogSys(ids);
		System.out.println(logSys);
	}
	@Test
	public void testListMenu() {
		List<Map<String,Object>> list = sysMenuDao.findObjects();
		System.out.println(list);
	}
	
	
	
	
}
