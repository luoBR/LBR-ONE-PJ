package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.bo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.entity.SysUserMenu;
@Mapper
public interface SysMenuDao {//SysMenuMapper
	
	/**
	 * 统计子菜单的个数-->如果为0 -->删除关联数据--->删除自身数据
	 * @param id
	 * @return
	 */
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChileCount(Integer id);
	/**
	 * 基于id删除菜单信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	/**查询菜单表中所有的菜单信息
	 * 一行菜单记录映射为一个map对象(key为字段名，value为字段对应的值),之后装到一个List集合中
	 * @return
	 * 使用map：1,开发效率高(不用设计封装类) 2,方便存储表关联的数据,执行效率没有封装类高。
	 * 缺点：1,值的类型不可控 ，2可读性差；
	 * 
	 * 也可以使用封装类来保存我们的数据，但是不规范，因为需要parentName在表中是没有的
	 * 但是也可以使用，可以多添加一条属性parentName 就OK了。
	 */
	List<Map<String,Object>> findObjects();
	/**
	 * 查询添加时选择的上级菜单
	 * @return
	 */
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	
	/**
	 * 将菜单信息写入数据库
	 * @return
	 */
	int insertObject(SysMenu sysMenu);
	/**
	 * 修改表
	 * @param sysMenu
	 * @return
	 */
	int updateObject(SysMenu sysMenu);
	/**
	 * 通过菜单id查询对应的权限
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(List<Integer> menuIds);
	/**
	 * 对每个用户登录之后资源可见做控制
	 * @param menuIds
	 * @return
	 */
	List<SysUserMenu> findMenusByIds(@Param("menuIds")List<Integer>menuIds);
}
