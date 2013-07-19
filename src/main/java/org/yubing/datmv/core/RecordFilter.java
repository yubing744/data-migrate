package org.yubing.datmv.core;

/**
 *	记录过滤器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-19
 */
public interface RecordFilter {
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	void init(MigrateContext context);
	
	/**
	 * 过滤记录
	 * 
	 * @param source
	 * @param migrateConfig
	 * @return
	 */
	Record filter(Record source, PageContext context, RecordFilterChain chain);

	/**
	 * 销毁
	 */
	void destroy();
}
