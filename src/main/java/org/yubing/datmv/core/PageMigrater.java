package org.yubing.datmv.core;

/**
 * 描述
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public interface PageMigrater {
	
	/**
	 * 迁移记录页
	 * 
	 * @param page
	 * @return
	 */
	RecordPage migrate(RecordPage page, MigrateContext context);
}
