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

		SimpleRecordContext context = new SimpleRecordContext(source, target);
		context.setPageContext(pageContext);

		if (items != null && !items.isEmpty()) {
			for (Iterator<ConfigItem> it = items.iterator(); it.hasNext();) {
				ConfigItem configItem = it.next();

				String destName = configItem.getName();
				String mappingKey = configItem.getMappingKey();
				MappingHandler mapHandler = configItem.getMappingHandler();

				if (destName != null) {
					DataField targetField = source.getDataField(mappingKey);
					targetField = copyFrom(targetField, destName);

					if (mapHandler != null) {
						context.setConfigItem(configItem);
						targetField = mapHandler.mapFrom(targetField, context);
					}

					target.addDataField(destName, targetField);
				}
			}
		} else {
			target = source;
		}

		return target;
	}

	protected DataField copyFrom(DataField sourceFiled, String destName) {
		SimpleDataField targetFiled = new SimpleDataField(destName);
		if (sourceFiled != null) {
			targetFiled.setType(sourceFiled.getType());
			targetFiled.setData(sourceFiled.getData());
		}
		return targetFiled;
	}
}
