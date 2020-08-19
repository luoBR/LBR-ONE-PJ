package com.cy.py.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.dao.SysRoleDao;

@SpringBootTest
public class SysRoleTest {
	
	@Autowired
	SysRoleDao sysRoleDao;
	@Test
	public void test1() {
		System.out.println(sysRoleDao.findPageObjects("系统管理员"));
	}
}
