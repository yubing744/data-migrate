package org.yubing.datmv.core;

/**
 * 描述
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public interface PageMigrater {
	
	/**
	 * 初始化
	 * 
	 * @param context
	 */
	void init(MigrateContext context);
	
	/**
	 * 迁移记录页
	 * 
	 * @param page
	 * @return
	 */
	RecordPage migrate(RecordPage page, MigrateContext context);

	/**
	 * 销毁
	 */
	void destroy();
}
