package com.cy.pj.sys.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cy.common.utils.ShiroUtils;
import com.cy.pj.common.annotation.ClearCache;
import com.cy.pj.common.annotation.RequiedCache;
import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.bo.PageObject;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserDept;
import com.cy.pj.sys.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
//@Transactional可以描述类 也可以描述方法  描述类表示事物的共性
	//描述方法表示事物的特性
	//做事物控制，表示这个注解描述的方法是一个事物 使用的也是AOP
	//  timeout:超时时长  isolation:事物隔离级别   readOnly=false表示开启在业务操作时不能访问 
	//不能并发访问  readOnly=true表示可以并发访问,只能是只读操作。
	//  rollbackFor 出现什么异常就回滚  propagation:传播特性
@Transactional(timeout=60,
	isolation = Isolation.READ_COMMITTED,
	readOnly = false,
	rollbackFor = Throwable.class,
	propagation = Propagation.REQUIRED)
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	SysUserRoleDao sysUserRoleDao;
	
	
	@Cacheable(value="dataCache")
	@RequiredLog(operation="分页查询")
	@Override
	public PageObject<SysUserDept> findPageObjects(String username, Integer pageCurrent) {
		String tName = Thread.currentThread().getName();
		System.out.println(tName);
		//1.参数校验
		if(pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("无效页码值");
		int pageSize=2;
		//2.设置分页参数
		Page<Object> page = PageHelper.startPage(pageCurrent, pageSize);
		List<SysUserDept> records = sysUserDao.findPageObjects(username);
		//3.查询当前页记录
		return  new PageObject<>(records, (int)page.getTotal(), page.getPages(), pageSize, pageCurrent);
	}
	
	@RequiresPermissions("sys:user:update")
	@CacheEvict(value = "dataCache",allEntries = true)
	@Override
	public int validById(Integer id, Integer valid) {
		//1.参数校验
		if(id==null || id <1)
			throw new IllegalArgumentException("id值无效");
		if(valid!=1&&valid!=0)
			throw new IllegalArgumentException("状态值无效");
		//2.修改用户状态
		int rows = sysUserDao.validById(id, valid, "admin");
		//3.验证结果
		if(rows==0)
			throw new ServiceException("记录已经不存在");
		return rows;
	}
	@CacheEvict(value = "dataCache",allEntries = true)
	//@ClearCache
	@Override
	public int insertObject(SysUser entity, Integer[] roleIds) {
		//1.对参数的校验
		if(entity==null)
			throw new IllegalArgumentException("传入的对象为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
		if(roleIds==null || roleIds.length==0)
			throw new IllegalArgumentException("请选中需要分配的角色信息!");
		//2.对密码的加密
		/* 可以使用MessafeDigest -- java 包的   可以使用 DigestUtils --spring框架的  
		 * 也可以使用第三方的框架
		 * spring中使用的加密工具类DigestUtils：使用md5加密 并且转为16进制 0x 
		 * 但是这个加密很容易就破解了。 可以使用加盐值增加密码难度。
		 * md5算法:消息再要(Message Digest)加密算法
		 *  特点： 1)不可逆 只能加密不能解密 要破解只能暴力破解
		 *  	  2)相同内容加密结果也相同。
		 * 
		*/
		//DigestUtils.md5DigestAsHex(entity.getPassword().getBytes());//是一个字节 要转为字节
		String salt=UUID.randomUUID().toString();//产生一个随机的字符串数据 
		//使用第三方的shiro进行加密
		/**第一个参数algorithmName是要加密的算法   source：要加密的密码 salt盐值  hashIterations加密次数
		SimpleHash sh = new SimpleHash(algorithmName, source, salt, hashIterations)
		   */
		SimpleHash sh = new SimpleHash("MD5", entity.getPassword(), salt, 1);
		String pwd=sh.toHex();//转为16进制
		entity.setPassword(pwd);
		entity.setSalt(salt);
		//3.添加用户信息
		sysUserDao.insertObject(entity);
		//4.添加用户于角色关系
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return 0;
	}
	@CacheEvict(value = "dataCache",allEntries = true)
	@Transactional(readOnly = true)
	//@ClearCache
	@Override
	public Map<String, Object> findObjectByUserId(Integer id) {
		//参数校验 
		if(id==null || id<1)
			throw new IllegalArgumentException("请选中要查询的数据");
		//2.做查询: (1)查询自身信息以及对于的部门信息 (2)查询用户id对于的角色关系
		SysUserDept user = sysUserDao.findObjectById(id);
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		Map<String,Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	@CacheEvict(value = "dataCache",allEntries = true)
	//@ClearCache
	@Override //修改
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验、
		if(entity==null)
			throw new IllegalArgumentException("修改的对象不能为空!");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("修改的用户名不能为空！");
		if(roleIds==null || roleIds.length==0)
			throw new IllegalArgumentException("请选中要修改的角色信息!");
		//2.修改自身信息
		sysUserDao.updateObject(entity);
		//3.删除用户对应的角色信息 在插入用户对应的角色信息
		sysUserRoleDao.deleteByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		return 0;
	}
	@Override
	public int updateUserPassword(String pwd, String newPwd, String cfgPwd) {
		//1.参数校验
		if(StringUtils.isEmpty(pwd))
			throw new IllegalArgumentException("密码不能为空");
		if(StringUtils.isEmpty(newPwd))
			throw new IllegalArgumentException("新密码不能为空");
		if(!newPwd.equals(cfgPwd))
			throw new IllegalArgumentException("两次输入的密码不一致");
		//校验输入的密码是否为原来的密码  可以从session中取到登录用户的密码
		SysUser user = ShiroUtils.getUserSbuject();
		String sourcePassword = user.getPassword();
		//对输入的密码进行加密之后与原密码对比
		SimpleHash sh = new SimpleHash("MD5", pwd, user.getSalt(), 1);
		//转为16进制
		String hashedInputPassword = sh.toHex();
 		if(!sourcePassword.equals(hashedInputPassword))
			throw new IllegalArgumentException("输入的原密码错误!");
		//2.更新密码
		String salt = UUID.randomUUID().toString();
		 SimpleHash newSh = new SimpleHash("MD5",newPwd,salt,1);
		 String hashedPassword = newSh.toHex();
		 int rows = sysUserDao.findPasswordByUserIds(user.getId(),hashedPassword,salt);
		 	if(rows==0)
		 		throw new ServiceException("用户已经不存在");
		return rows;
	}
}

