package org.yubing.datmv.filter;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;
import org.yubing.datmv.core.RecordFilterChain;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;

public class XHeaderWriteRecordFilter implements RecordFilter {

	public Record filter(Record source, PageContext context, RecordFilterChain chain) {
		Integer pageNum = (Integer) context.getAttribute("cur.page.num");
		Boolean hasWrited = (Boolean) context
				.getAttribute("header.has.write.flag");

		Record target = chain.filter(source, context);
		
		if (pageNum == 1 && hasWrited == null) {
			RecordPage targetPage = context.getTargetPage();
			if (targetPage!=null && target != null) {
				Record header = makeHeaderRecord(target);
				if (header != null) {
					targetPage.writeRecord(header);
					context.setAttribute("header.has.write.flag", new Boolean(
							true));
				}
			}
		}
		
		return target;
	}

	protected Record makeHeaderRecord(Record target) {
		if (target != null) {
			Record header = new SimpleRecord();
			expandRecord(target, "", header);
			return header;
		}

		return null;
	}

	protected void expandRecord(Record target, String parentName, Record header) {
		for (Iterator<DataField> it = target.iterator(); it.hasNext();) {
			DataField field = it.next();
			
			String destName = null;
			
			if (StringUtils.isBlank(parentName)) {
				destName = field.getName();
			} else {
				destName = parentName + "_" + field.getName();
			}
	
			Object data = field.getData();
			
			if (!(data instanceof RecordPage)) {
				DataField headerField = new SimpleDataField(destName, DataType.STRING);
				headerField.setData(destName);
				header.addDataField(headerField);
			} else {
				RecordPage page = (RecordPage)data;
				
				if (page.hasNext()) {
					Record target2 = page.readRecord();
					expandRecord(target2, destName, header);
				}
			}
		}
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
					header.addDataField(headerField);
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
