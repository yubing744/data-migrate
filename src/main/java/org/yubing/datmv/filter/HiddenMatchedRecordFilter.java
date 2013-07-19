package org.yubing.datmv.filter;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.RecordFilterChain;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;

public class HiddenMatchedRecordFilter implements RecordFilter {
	private String datakey;
	private String regex;

	public HiddenMatchedRecordFilter(String datakey, String regex) {
		this.datakey = datakey;
		this.regex = regex;
	}

	public Record filter(Record source, PageContext context, RecordFilterChain chain) {

		if (source != null) {
			DataField field = source.getDataField(datakey);
			if (field != null) {
				Object data = field.getData();
				if (data != null && data instanceof String) {
					String strData = (String)data;
					if (strData.trim().matches(regex)) {
						return null;
					}
				}
			}
		}

		return chain.filter(source, context);
	}

	public void init(MigrateContext context) {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
