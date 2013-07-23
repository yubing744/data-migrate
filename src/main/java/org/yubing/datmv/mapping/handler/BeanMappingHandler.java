package org.yubing.datmv.mapping.handler;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordContext;

/**
 *	POJO对象映射处理器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-23
 */
public class BeanMappingHandler implements MappingHandler {

	public DataField mapFrom(DataField targetField, RecordContext context) {
		ConfigItem configItem = context.getConfigItem();
		String mappingKey = configItem.getMappingKey();
		
		String[] segs = mappingKey.split("\\.");
		
		Object data = null;
		
		if (segs.length > 0) {
			Record record = context.getSource();
			data = record.getFieldData(segs[0]);
		}
		
		if (data != null && segs.length > 1) {
			segs = (String[])ArrayUtils.subarray(segs, 1, segs.length);
			String path = StringUtils.join(segs, ".");
			
			try {
				targetField.setData(BeanUtils.getProperty(data, path));
			} catch (Exception e) {
				throw new RuntimeException("error in get property with key:" + mappingKey, e);
			}
		}
		
		return targetField;
	}

}
