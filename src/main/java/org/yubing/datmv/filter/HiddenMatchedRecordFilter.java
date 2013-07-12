package org.yubing.datmv.filter;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.FilterChain;
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

	public Record filter(Record source, PageContext context, FilterChain chain) {

		if (source != null) {
			DataField field = source.getDataField(datakey);
			if (field != null) {
				String data = field.getData();
				if (data != null && data.trim().matches(regex)) {
					return null;
				}
			}
		}

		return chain.filter(source, context);
	}

}
