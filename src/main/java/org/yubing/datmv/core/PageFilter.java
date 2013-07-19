package org.yubing.datmv.core;

public interface PageFilter {
	
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	void init(MigrateContext context);
	
	/**
	 * 过滤记录页
	 * 
	 * @param source
	 * @param context
	 * @param chain
	 * @return
	 */
	RecordPage filter(RecordPage source, MigrateContext context, PageFilterChain chain);

	/**
	 * 销毁
	 */
	void destroy();
}
