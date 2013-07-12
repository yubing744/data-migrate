package org.yubing.datmv.core;

/**
 * 数据记录写入器
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface DataWriter extends ResourceAccess {
	/**
	 * 写入一条记录
	 * 
	 * @param record
	 */
	void writeRecord(Record record);
}
