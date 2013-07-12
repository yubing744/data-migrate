package org.yubing.datmv.config;

import org.yubing.datmv.core.internal.SimpleConfigItem;

/**
 * 迁移配置简单实现
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public class SimpleMigrateConfig extends AbstractMigrateConfig {

	/**
	 * 添加配置项
	 * 
	 * @param name 配置项名称
	 * @param mappingKey 配置项目映射Key
	 */
	public void addConfigItem(String name, String mappingKey) {
		SimpleConfigItem item = new SimpleConfigItem();
		item.setName(name);
		item.setMappingKey(mappingKey);
		this.configItems.add(item);
	}
}
