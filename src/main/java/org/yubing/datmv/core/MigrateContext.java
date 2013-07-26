package org.yubing.datmv.core;

import java.util.Map;

/**
 * 数据迁移上下文接口
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public interface MigrateContext {
	
	/**
	 * 获取父亲上下文
	 * 
	 * @return
	 */
	MigrateContext getParent();
	
	/**
	 * 设置迁移配置
	 * 
	 * @param migrateConfig
	 */
	void setMigrateConfig(MigrateConfig migrateConfig);

	/**
	 * 获取迁移配置
	 * 
	 * @return
	 */
	MigrateConfig getMigrateConfig();

	/**
	 * 添加属性
	 * 
	 * @param key
	 * @param value
	 */
	void setAttribute(String key, Object value);

	/**
	 * 获取属性
	 * 
	 * @param key
	 * @return
	 */
	Object getAttribute(String key);
	
	
	/**
	 * 获取参数
	 * @param key
	 * @return
	 */
	String getParameter(String key);
	
	/**
	 * 设置参数
	 * 
	 * @param key
	 * @param value
	 */
	void setParameter(String key, String value);

	/**
	 * 获取参数Map
	 * 
	 * @return
	 */
	Map<String, String> getParameterMap();
	
	
	/**
	 * 获取属性Map
	 * 
	 * @return
	 */
	Map<String, Object> getAttributeMap();
}
