package org.yubing.datmv.mapping.handler;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.RecordContext;

public class AutoIncNumberMappingHandler implements MappingHandler {

	public DataField mapFrom(DataField targetField, ConfigItem configItem, RecordContext context) {
		MigrateContext ctx = context.getMigrateContext();
		
		Integer num = (Integer)ctx.getAttribute("cur.num");
		targetField.setData(num);
		
		return targetField;
	}
}
