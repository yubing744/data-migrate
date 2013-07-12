package org.yubing.datmv.core;

/**
 * 记录页接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface RecordPage extends DataReader, DataWriter {
	
	/**
	 * 记录页大小
	 * @return
	 */
	int pageSize();
}
