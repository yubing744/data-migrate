package org.yubing.datmv.core;

import java.util.List;

/**
 * 数据迁移配置接口
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public interface MigrateConfig {

	/**
	 * 获取配置项
	 * 
	 * @return
	 */
	List<ConfigItem> getConfigItems();

	/**
	 * 获取记录过滤器
	 * 
	 * @return
	 */
	List<RecordFilter> getRecordFilters();

	/**
	 * 获取数据迁移监听器
	 * 
	 * @return
	 */
	List<MigrateListener> getMigrateListeners();

	/**
	 * 获取数据源读取器
	 * 
	 * @return
	 */
	PageReader getSourceReader();

	/**
	 * 获取迁移目标写入器
	 * 
	 * @return
	 */
	PageWriter getTargetWriter();
	
	
	/**
	 * 获取page预览处理器
	 * 
	 * @return
	 */
	PagePreview getPagePreview();
}
