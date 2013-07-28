package org.yubing.datmv.core;

import java.util.Iterator;


/**
 * 迁移记录接口
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-9
 */
public interface Record {
	
	/**
	 * 添加一项数据域
	 * 
	 * @param key
	 * @param dataField
	 */
	void addDataField(DataField dataField);

	/**
	 * 删除一项数据域
	 * 
	 * @param key
	 */
	void removeDataField(String name);
	
	/**
	 * 获取数据域
	 * 
	 * @param name
	 * @return
	 */
	DataField getDataField(String name);
	
	/**
	 * 获取记录列数
	 * 
	 * @return
	 */
	int size();
	
	/**
	 * 获取域数据
	 * 
	 * @param name
	 * @return
	 */
	Object getFieldData(String name);
	
	/**
	 * 获取迭代器
	 * 
	 * @return
	 */
	Iterator<DataField> iterator();
}
