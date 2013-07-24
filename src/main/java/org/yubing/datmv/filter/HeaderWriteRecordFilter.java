package org.yubing.datmv.filter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.RecordFilterChain;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;

public class HeaderWriteRecordFilter implements RecordFilter {

	public Record filter(Record source, PageContext context, RecordFilterChain chain) {
		Integer pageNum = (Integer) context.getAttribute("cur.page.num");
		Boolean hasWrited = (Boolean) context
				.getAttribute("header.has.write.flag");
		if (pageNum == 1 && hasWrited == null) {
			RecordPage target = context.getTarget();
			if (target != null) {
				MigrateConfig config = context.getMigrateConfig();
				Record header = makeHeaderRecord(config.getConfigItems());
				if (header != null) {
					target.writeRecord(header);
					context.setAttribute("header.has.write.flag", new Boolean(
							true));
				}
			}
		}

		return chain.filter(source, context);
	}

	protected Record makeHeaderRecord(Record target) {
		if (target != null) {
			Record header = new SimpleRecord();

			Set<String> keys = target.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				DataField field = target.getDataField(key);

				String destName = field.getName();

				DataField headerField = new SimpleDataField(destName, DataType.STRING);
				headerField.setData(destName);
				header.addDataField(destName, headerField);
			}

			return header;
		}

		return null;
	}

	protected Record makeHeaderRecord(List<ConfigItem> items) {
		if (items != null && !items.isEmpty()) {
			Record header = new SimpleRecord();
			for (Iterator<ConfigItem> it = items.iterator(); it.hasNext();) {
				ConfigItem configItem = it.next();
				String destName = configItem.getName();

				if (destName != null) {
					DataField headerField = new SimpleDataField(destName, DataType.STRING);
					headerField.setData(destName);
					header.addDataField(destName, headerField);
				}
			}
			return header;
		}
		
		return null;
	}

	public void init(MigrateContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
