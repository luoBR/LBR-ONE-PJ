package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.bo.CheckBox;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysRoleBO;
import com.cy.pj.sys.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	SysUserRoleDao sysUserRoleDao;
	
	@Override
	/**查询有无记录信息*/
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.做校验 判断pageCurrent
		if(pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("无效页码!");
//		int rowCount=0;
		try {
			//2.查询总记录数，并校验
		//rowCount = sysRoleDao.getRowCount(name);
//			if(rowCount==0)
//			throw new ServiceException("没有获取到用户信息!");
		//一般在业务层的数据都是使用基本类型，在封装类中才使用封装类型。
		int pageSize=2;
		/**使用pageHelper进行分页   分页  :pageCurrent             长度    :pageSize   */
		//2.1设置查询参数	
		//底层在做查询时会封装成为一个Page对象，相当于PageObject这个对象，这里不想去修改PageObject了。继续使用它封装。
		Page<SysRole> page = PageHelper.startPage(pageCurrent, pageSize);
		//2.2启动查询操作  底层使用的是一个线程绑定机制，把2.1 2.2 绑定在一起。
		List<SysRole> records = sysRoleDao.findPageObjects(name);
		//2.3封装查询结果  总记录数: (int)page.getTotal()  总页数： page.getPages()
		return new PageObject<>(records, (int)page.getTotal(), page.getPages(), pageSize, pageCurrent);
		
	
		//int startIndex=(pageCurrent-1)*pageSize;
		//3.查询当前页角色记录 封装查询结果并返回 原来版本
		//List<SysRole> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//return new PageObject<>(records, rowCount, pageSize, pageCurrent);
		
		}catch(Throwable e) {
			e.printStackTrace();
			throw new ServiceException("服务器出现异常!");
		}
		
	}

	/**
	 * 做删除角色信息的操作
	 */
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null || id<1)
			throw new IllegalArgumentException("没有正常的选择数据!");
		//2.删除关系数据 
		//2.1删除角色与菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//2.2删除角色与用户关系数据
		sysUserRoleDao.deleteObjectByRoleId(id);
		//3.删除自身信息
	    int rows = sysRoleDao.deleteObject(id);
	    if(rows==0)
	    	throw new ServiceException("没有删除的信息！");
		return 0;
	}
	/**
	 * 保存角色信息以及保存角色和菜单信息
	 */
	@Override
	public int insertObject(SysRole entity, Integer[] menuIds) {
		//1.做参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存的角色对象不能为空!");
		if(StringUtils.isEmpty(entity.getName()))//使用spring框架的一个工具类
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null || menuIds.length==0)
			throw new IllegalArgumentException("没有给角色分配权限");
		//2.保存角色自身信息，获取到它的id主键
		int rows = sysRoleDao.insertObject(entity);
		//3.保存角色与菜单 的信息
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}

	@Override
	public SysRoleBO doFindObjectById(Integer id) {
		//1.做校验
	if(id==null || id<1)
		throw new IllegalArgumentException("id值无效!");
		//2.基于id查询角色自身信息
		//3.基于id查询角色于菜单的对应关系信息
	SysRoleBO objectById = sysRoleDao.findObjectById(id);
		//4.封装为有一个对象传到客户端
		return objectById;
	}
	@Override
	public int doUpdateObject(SysRole sysRole, Integer[] menuIds) {
		//1.做校验
		if(sysRole==null)
			throw new IllegalArgumentException("修改的对象不能为空");
		if(StringUtils.isEmpty(sysRole.getName()))
			throw new IllegalArgumentException("传入的修改角色名称不能为空");
		if(menuIds==null || menuIds.length==0)
			throw new IllegalArgumentException("传入的授权信息不能为空!");
		//2.根据id修改角色自身信息
		sysRoleDao.updateObject(sysRole);
		//3.根据id删除角色与菜单表的旧的关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(sysRole.getId());
		//4.根据id新增角色与菜单表的新的数据关系
		sysRoleMenuDao.insertObjects(sysRole.getId(), menuIds);
		return 0;
	}

	@Override
	public List<CheckBox> findObjects() {
		
		return sysRoleDao.findObjects();
	}
}
