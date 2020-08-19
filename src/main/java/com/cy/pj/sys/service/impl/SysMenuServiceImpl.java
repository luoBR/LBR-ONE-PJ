package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.bo.Node;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.entity.SysUserMenu;
import com.cy.pj.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	//多个属性也必须加上这个注解
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;//服务于其他业务，不用定义自己的service，只是为了存储数据。
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	//spring中的缓存 Map<String,Map>
	@Cacheable(value = "menuCache")//具体cache由aop实现 @Cacheable描述的方法为切入方法
	@Override
	public List<Map<String, Object>> findObject() {
		System.out.println("menuCache");
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if(list==null||list.size()==0) {
			throw new  ServiceException("没有找到数据！");
		}
		return list;
	}
	/**
	 * 基于菜单id删除菜单信息以及菜单对应的上级菜单信息
	 */
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null || id<1)
			throw new IllegalArgumentException("id值无效");
		//2.基于id统计子菜单数并进行校验
		int childCount = sysMenuDao.getChileCount(id);
		if(childCount>0)
			throw new ServiceException("有子菜单不能删除!");
		//3.删除菜单角色关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.删除菜单自身信息
		int rows = sysMenuDao.deleteObject(id);
			if(rows==0)
				throw new ServiceException("记录可能不存在!");
		return rows;
	}
	@Override/** 做树状图的数据*/
	public List<Node> findZtreeMenuNodes() {
		List<Node> nodes = sysMenuDao.findZtreeMenuNodes();
		if(nodes==null || nodes.size()==0)
			throw new IllegalArgumentException("没有获取到数据!");
		
		return nodes;
	}
	@Override
	public int savaObject(SysMenu sysMenu) {
		sysMenuDao.insertObject(sysMenu);
		return 0;
	}
	@Override
	public int updateObject(SysMenu sysMenu) {
		int object = sysMenuDao.updateObject(sysMenu);
		if(object==0)
			throw new IllegalArgumentException("删除失败");
		return 0;
	}
	@Override
	public List<SysUserMenu> findUserMenuByUserId(Integer id) {
		// 1.根据用户id获取到角色信息
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		//2.根据角色信息获取菜单信息
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		//3.根据菜单id获取一级菜单或者二级菜单
		List<SysUserMenu> data = sysMenuDao.findMenusByIds(menuIds);
		//4
		if(StringUtils.isEmpty(data))
			throw new ServiceException("没有资格查询 ！ 请充钱！");
		return data;
	}
}
