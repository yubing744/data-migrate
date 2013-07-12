package org.yubing.datmv.core;

/**
 * 资源访问接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface ResourceAccess {
	/**
	 * 打开资源
	 */
	void open();
	
	/**
	 * 释放资源
	 */
	void release();
}
