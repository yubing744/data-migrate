package org.yubing.datmv.core;

public interface RecordFilter {
	
	/**
	 * 过滤记录
	 * 
	 * @param source
	 * @param migrateConfig
	 * @return
	 */
	Record filter(Record source, PageContext context, FilterChain chain);
}
