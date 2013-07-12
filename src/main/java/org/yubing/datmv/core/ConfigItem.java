package org.yubing.datmv.core;

/**
 * 描述 数据迁移配置项接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface ConfigItem {

	/**
	 * 获取配置名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取映射 key
	 * 
	 * @return
	 */
	String getMappingKey();

	/**
	 * 获取映射处理器
	 * 
	 * @return
	 */
	MappingHandler getMappingHandler();

}
