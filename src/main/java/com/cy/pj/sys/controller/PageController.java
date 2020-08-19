package com.cy.pj.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.common.utils.ShiroUtils;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.entity.SysUserMenu;
import com.cy.pj.sys.service.SysMenuService;
/**
 * 所有涉及到页面返回的controller都放在这个类中
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class PageController {
	@Autowired
	private SysMenuService sysMenuService;
	
		@RequestMapping("doIndexUI")
		public String doIndexUI(Model model) {
			//把登录的用户名带到客户端显示
			SysUser user = ShiroUtils.getUserSbuject();
			model.addAttribute("user", user);
			Integer id = user.getId();
			List<SysUserMenu> userMenus = sysMenuService.findUserMenuByUserId(id);
			model.addAttribute("userMenus", userMenus);
			return "starter";
		}
		@RequestMapping("doLoginUI")
		public String doLoginUI() {
			return "login";
		}
		//返回记录日志的
		@RequestMapping("/log/log_list")
		
		public String doLogUI() {
			return "sys/log_list";
			//$("#"id).load(url)把返回的数据响应到响应的位置。
		}
		/*//返回分页页面
		@RequestMapping("doPageUI")
		public String doPageUI() {y
			return "common/page";
		}*/
		@RequestMapping("/menu/menu_list")
		public String doMenuUI() {
			return "sys/menu_list";
		}
		
		/**
		 * rest风格 /{module}/{moduleUI} {}表示这里是一个变量
		 * @PathVariable:使用这个注解描述的表示可以获取到传入的{}里面的值
		 * 传入到方法参数里面。
		 * @param moduleUI
		 * @return
		 */
		@RequestMapping("/{module}/{moduleUI}")
		public String doModuleUI(@PathVariable String moduleUI) {
			return "sys/"+moduleUI;
		}
		
}
