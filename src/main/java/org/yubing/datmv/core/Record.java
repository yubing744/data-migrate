package org.yubing.datmv.core;

import java.util.Map;
import java.util.Set;

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
	void addDataField(String key, DataField dataField);

	/**
	 * 删除一项数据域
	 * 
	 * @param key
	 */
	void removeDataField(String key);
	
	/**
	 * 获取数据域
	 * 
	 * @param key
	 * @return
	 */
	DataField getDataField(String key);
	
	/**
	 * 获取记录列数
	 * 
	 * @return
	 */
	int getColSize();
	
	
	/**
	 * 获取记录索引集合
	 * 
	 * @return
	 */
	Set<String> keySet();

	/**
	 * 获取域数据
	 * 
	 * @param key
	 * @return
	 */
	Object getFieldData(String key);

	/**
	 * 把当前记录值转换为一个Map
	 * 
	 * @return
	 */
	Map<String, Object> toMap();
}
