package org.yubing.datmv.core;


/**
 * 数据域映射出来器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public interface MappingHandler {

	/**
	 * 根据原记录映射出目标数据域
	 * 
	 * @param source
	 * @param targetField
	 * @return
	 */
	DataField mapFrom(DataField targetField, ConfigItem configItem, RecordContext context);
}
