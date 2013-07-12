package org.yubing.datmv.core;

/**
 * 数据迁移上下文接口
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public interface MigrateContext {
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
}
