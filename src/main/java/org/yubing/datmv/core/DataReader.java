package org.yubing.datmv.core;

/**
 * 数据记录读取器
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface DataReader extends ResourceAccess {

	/**
	 * 是否存在下一条记录
	 * 
	 * @return
	 */
	boolean hasNext();

	/**
	 * 读取一条记录，并将当前行移到下一行
	 * 
	 * @return
	 */
	Record readRecord();

	/**
	 * 重置当前行
	 * 
	 */
	void reset();
}
