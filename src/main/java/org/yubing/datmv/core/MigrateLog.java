package org.yubing.datmv.core;

/**
 * 迁移日志接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface MigrateLog {

	/**
	 * 记录消息
	 * 
	 * @param msg
	 */
	void log(String msg);

}
