package org.yubing.datmv.core;

/**
 * 过滤链接口
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public interface RecordFilterChain {
	
	/**
	 * 过滤
	 * 
	 * @param source
	 * @param migrateConfig
	 * @return
	 */
	Record filter(Record source, PageContext context);
}
