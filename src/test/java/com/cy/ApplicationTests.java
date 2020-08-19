package com.cy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
class ApplicationTests {

	@Autowired
	SysLogDao sysLogDao;
	
	@Test
	public void test() {
		int rows = sysLogDao.getRowCount("ad");
		System.out.println(rows);
		
	}
	
	@Test
	public void test2() {
		List<SysLog> list = sysLogDao.findPageObjects("", 5, 10);
		for (SysLog sysLog : list) {
			System.out.println(sysLog);
		}
	}
}
