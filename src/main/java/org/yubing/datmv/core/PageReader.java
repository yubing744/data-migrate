package org.yubing.datmv.core;

/**
 * 记录页读取器
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface PageReader extends ResourceAccess {
	
	/**
	 * 读取一页记录
	 * 
	 * @param pageSize 页大小
	 * @return
	 */
	RecordPage readPage(int pageSize);
	
	/**
	 * 是否还有记录可读
	 * 
	 * @return
	 */
	boolean hasNext();
}
