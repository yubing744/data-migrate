package org.yubing.datmv.mapping.handler;


import java.util.Date;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.RecordContext;

/**
 *	当前时间映射处理器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-16
 */
public class CurrentDateMappingHandler implements MappingHandler {

	public DataField mapFrom(DataField targetField, RecordContext context) {
		targetField.setData(new Date());
		return targetField;
	}
}
