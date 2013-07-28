package org.yubing.datmv.core.internal;

import java.util.Iterator;
import java.util.List;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordMigrater;

public class SimpleRecordMigrater implements RecordMigrater {

	public Record migrate(Record source, PageContext pageContext) {
		MigrateConfig migrateConfig = pageContext.getMigrateConfig();

		List<ConfigItem> items = migrateConfig.getConfigItems();
		Record target = new SimpleRecord();

		SimpleRecordContext context = new SimpleRecordContext(pageContext, source, target);

		if (items != null && !items.isEmpty()) {
			for (Iterator<ConfigItem> it = items.iterator(); it.hasNext();) {
				ConfigItem configItem = it.next();

				String destName = configItem.getName();
				String destType = configItem.getType();
				Object defaultVal = configItem.getDefaultValue();
				
				String mappingKey = configItem.getMappingKey();

				if (destName != null) {
					DataField sourceField = source.getDataField(mappingKey);
					DataField targetField = copyFrom(sourceField, destName, destType, defaultVal);
					target.addDataField(targetField);
				}
			}
			
			for (Iterator<ConfigItem> it = items.iterator(); it.hasNext();) {
				ConfigItem configItem = it.next();

				String destName = configItem.getName();

				MappingHandler mapHandler = configItem.getMappingHandler();

				if (destName != null) {
					DataField targetField = target.getDataField(destName);

					if (mapHandler != null) {
						targetField = mapHandler.mapFrom(targetField, configItem, context);
					}

					target.addDataField(targetField);
				}
			}
		} else {
			target = source;
		}

		return target;
	}

	protected DataField copyFrom(DataField sourceFiled, String destName, String destType, Object defaultVal) {
		SimpleDataField targetFiled = new SimpleDataField(destName, destType);
		targetFiled.setData(defaultVal);
		
		if (sourceFiled != null) {
			targetFiled.setData(sourceFiled.getData());
		}
		
		return targetFiled;
	}
}
