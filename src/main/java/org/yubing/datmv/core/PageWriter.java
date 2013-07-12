package org.yubing.datmv.core;

/**
 * 记录页写入器
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface PageWriter extends ResourceAccess {
	
	/**
	 * 写入一页记录到目标
	 * 
	 * @param pageRecord
	 */
	void writePage(RecordPage pageRecord);
}
