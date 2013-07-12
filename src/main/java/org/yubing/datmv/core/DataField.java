package org.yubing.datmv.core;

/**
 * 记录数据域
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public interface DataField {

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取数据类型
	 * 
	 * @return
	 */
	String getType();

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	String getData();

	/**
	 * 设置数据类型
	 * 
	 * @param type
	 */
	void setType(String type);

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	void setData(String data);
}
