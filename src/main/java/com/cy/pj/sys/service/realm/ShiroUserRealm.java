package com.cy.pj.sys.service.realm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;

/**
 * 基于此对象获取用户认证和授权信息并封装
 * @author Administrator
 *
 */
@Component
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	/**负责授权信息的获取和封装:
	 * 为了提高查询性能，还可以将用户信息进行缓存，具体的缓存对象使用了
	 * SoftHashMap<>一个软引用，使用SoftHashMap的好处，可以不设置容量上限，达到一定程度可以被GC直接回收
	 * SoftHashMap 其中的key为当前用户身份，doGetAuthorizationInfo()方法的返回值作为value
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1.获取登录的用户信息 id 
		SysUser user =(SysUser)principals.getPrimaryPrincipal();
		Integer id = user.getId();
		//2.做校验 基于id查询用户对应的角色id 
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		if(roleIds==null || roleIds.size()==0)
			throw new AuthorizationException();
		//3.基于角色id查询对应的菜单信息 menu_id 
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds);
		if(menuIds==null || menuIds.size()==0)
			throw new AuthorizationException();
		//4.基于菜单信息获取权限信息
		List<String> permissions = sysMenuDao.findPermissions(menuIds);
		if(permissions==null || permissions.size()==0)
			throw new AuthorizationException();
		//5.对权限信息封装并返回
		//5.1 先去除重复的信息 使用set集合去重
		Set<String> set = new HashSet<String>();
		//5.2迭代存放权限的集合
		Iterator<String> list = permissions.iterator();
		while(list.hasNext()) {
			String permission = list.next();
			set.add(permission);
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		return info;
	}
	/**负责认证信息的封装*/
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.获取用户端输入的用户名
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username = upToken.getUsername();
		//2.基于用户名获取对象
		SysUser user = sysUserDao.findUserName(username);
		//3.判断用户对象是否存在
		if(user==null) throw new UnknownAccountException();
		//4.判定用户的状态是否为禁用
		if(user.getValid()==0) throw new LockedAccountException();
		//5.封装用户认证信息
		//需要把getSalt转为 SimpleAuthenticationInfo 参数需要的参数类型ByteSource对象 所以需要这样转
		ByteSource salt = ByteSource.Util.bytes(user.getSalt());
		SimpleAuthenticationInfo info =
				new SimpleAuthenticationInfo(user, //principal:用户登录成功的身份 登录成功之后把信息存储到session中
						user.getPassword(), //hashedCredentials :用户的凭证
						salt, //credentialsSalt 凭证盐
						"ShiroUserRealm"); //realmName realm的名称
		
		return info;
	}
	/**负责获取加密凭证匹配器对象 对输入的密码进行加密*/
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		//1.构建凭证匹配器
		HashedCredentialsMatcher hMatcher = new HashedCredentialsMatcher();
		//2.加密算法
		hMatcher.setHashAlgorithmName("MD5");
		//3.加密次数
		hMatcher.setHashIterations(1);
		return hMatcher;
	}
	
	
}
