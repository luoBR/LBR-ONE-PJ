package com.cy.pj.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class DefaultMapCache {
	
	/**
	 * ConcurrentHashMap 线程安全的map集合 使用cas算法 
	 * 这里的map是作为一个容器
	 */
	private Map<Object,Object> cache = new ConcurrentHashMap<>();
	/**
	 * 存放数据到cache中定义   cache到堆内存中
	 * @param key
	 * @param value
	 */
	public void putObject(Object key,Object value) {
		cache.put(key, value);
	}
	public Object getObject(Object key) {
		return cache.get(key);
	}
	public void doClear() {
		cache.clear();
	}
}
