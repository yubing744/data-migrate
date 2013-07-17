package org.yubing.datmv.core;

/**
 * 数据迁移监听器
 *
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public interface MigrateListener {
	
	/**
	 * 事件响应
	 * 
	 * @param context
	 */
	void onEvent(String eventName, MigrateContext context);
}
