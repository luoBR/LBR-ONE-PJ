package com.cy.py.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysMenuDao;

@SpringBootTest
public class SysMenuTest {
	@Autowired
	SysMenuDao sysMenuDao;
	
	@Test
	public void test1() {
		int count = sysMenuDao.getChileCount(8);
		System.out.println(count);
	}
}	
