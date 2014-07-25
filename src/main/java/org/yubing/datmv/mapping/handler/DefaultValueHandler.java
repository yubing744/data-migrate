package org.yubing.datmv.mapping.handler;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.RecordContext;

public class DefaultValueHandler implements MappingHandler {
    private final static String DEFAULT_VALUE = "无信息";

	public DataField mapFrom(DataField targetField, ConfigItem configItem,
			RecordContext context) {
		Object data = targetField.getData();
		
		if (data == null || StringUtils.isBlank(data.toString())) {
			targetField.setData(DEFAULT_VALUE);
		}
		
		return targetField;
	}

}