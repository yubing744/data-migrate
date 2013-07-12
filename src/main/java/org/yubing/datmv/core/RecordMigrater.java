package org.yubing.datmv.core;


/**
 * 一条记录迁移接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface RecordMigrater {
	
	/**
	 * 根据配置迁移一条记录
	 * 
	 * @param source
	 * @param migrateConfig
	 * @return
	 */
	Record migrate(Record source, PageContext context);
}
