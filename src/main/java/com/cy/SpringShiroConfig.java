package com.cy;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用shiro加上spring做登录拦截 
 * @author Administrator
 * @Configuration 注解描述的类为一个配置对象,
 * 此对象也会交给spring管理
 */
@Configuration
public class SpringShiroConfig {
	/**
	 * shiro session
	 * @return
	 */
	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager session = new DefaultWebSessionManager();
		session.setGlobalSessionTimeout(7*24*60*60);
		return session;
	}
	/**
	 * 做一个缓存 shiro的缓存 
	 * @return
	 */
	@Bean
	public CacheManager shiroCacheManager() {
		return new MemoryConstrainedCacheManager();
	}
	
	/**设置记住我功能的配置 cookie*/
	@Bean
	public RememberMeManager rememberMeManager() {
		//这只是一个管理器对象CookieRememberMeManager
		CookieRememberMeManager cManager = new CookieRememberMeManager();
		//需要创建一个cookie对象
		SimpleCookie cookie = new SimpleCookie("rememberMe");//参数是cookie名称
		cookie.setMaxAge(7*24*60);
		cManager.setCookie(cookie);
		return cManager;
	}

	/**
	 * @Bean注解一般要结合@Configuration注解使用，用于描述方法，表示该方法的返回值要交给spring bean
	 * 管理  key为方法名小写，或者通过@Bean指定
	 * SecurityManager(注意包名 是shiro的) shiro框架的核心对象，可以对登录的验证，权限的控制等
	 * @return
	 */
	@Bean
	public SecurityManager sercurityManager(Realm realm,CacheManager shiroCacheManager,
			RememberMeManager rememberMeManager,
			SessionManager session) { //自动注入给sercurityManager
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);//把从数据库查到的数据拿过来
		securityManager.setCacheManager(shiroCacheManager);//把缓存注入给securityManager
		securityManager.setRememberMeManager(rememberMeManager);//把cookie设置注入给SecurityManager
		securityManager.setSessionManager(session);
		return securityManager;
	}
	/**
	 * 用于产生过滤器. 通过ShiroFilterFactoryBean 创建一个工厂  通过工厂创建过滤器
	 * 通过 过滤器对数据进行过滤
	 * 将 SecurityManager对象注入给 ShiroFilterFactoryBean 对象，之后
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		//1.1创建ShiroFilterFactoryBean对象
		ShiroFilterFactoryBean fBean = new ShiroFilterFactoryBean();
		//1.2设置安全认证授权对象 检查对象有没有登录
		fBean.setSecurityManager(securityManager);
		//1.3设置登录页面 检查对象如果没有登录，就会跳转到指定的页面
		fBean.setLoginUrl("/doLoginUI");
		//2.设置过滤规则 
		 //定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
		//使用LinkedHashMap能够保证key的添加顺序 
        LinkedHashMap<String,String> map=new LinkedHashMap<>();
        //静态（目录static下的资源)资源允许匿名访问:"anon"
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/modules/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        map.put("/user/doLogin", "anon");
        map.put("/doLogout","logout"); 
       // map.put("/doIndexUI","anon");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        //map.put("/**","authc"); //没有使用记住我的功能 使用authc
        map.put("/**","user");//当选择记住我功能，使用这个user
      //2.设置过滤规则 
        fBean.setFilterChainDefinitionMap(map);
        return fBean;
	}
	/**读取进行权限控制的方法 需要加一个标识 例如:@RequiresPermissions描述的
	 *	问题?谁去读这些标识呢?
	 *Spring底层借助Advisor对象获取切入点，并在切入点上引用Advice(通知)
	 *底层也会把我们写的切面 @pointcut @advice 解析完之后封装到AspectPointcutAdvisor中
	 *AspectPointcutAdvisor 相当于一个顾问，告诉spring要为哪些对象进行功能扩展
	 *底层又是使用了BeanPostProcessor 是在bean初始化的时候，如果找到需要参数代理对象 就会产生代理对象
	 *为目标实现功能扩展。
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisorr(
			SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
}
