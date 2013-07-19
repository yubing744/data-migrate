package org.yubing.datmv.core.internal;

import java.util.Iterator;
import java.util.List;

import org.yubing.datmv.core.RecordFilterChain;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public class SimpleRecordFilterChain implements RecordFilterChain {
	private RecordFilter lastFilter;
	private Iterator<RecordFilter> it;

	public SimpleRecordFilterChain(List<RecordFilter> filters, RecordFilter lastFilter) {
		this.lastFilter = lastFilter;
		this.it = filters.iterator();
	}

	public Record filter(Record source, PageContext context) {
		Record target = source;
		
		if (it.hasNext()) {
			RecordFilter nextFilter = it.next();
			target = nextFilter.filter(target, context, this);
		} else {
			target = lastFilter.filter(target, context, this);
		}
		
		return target;
	}

}
