package org.yubing.datmv.mapping.handler;


import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.RecordContext;

public class CurrentDateMappingHandler implements MappingHandler {

	FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
	
	public DataField mapFrom(DataField targetField, RecordContext context) {
		targetField.setData(formatter.format(new Date()));
		return targetField;
	}
}
