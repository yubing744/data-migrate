package org.yubing.datmv.core.internal;

import java.util.Iterator;
import java.util.List;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageFilter;
import org.yubing.datmv.core.PageFilterChain;
import org.yubing.datmv.core.RecordPage;

public class SimplePageFilterChain implements PageFilterChain {

	private PageFilter lastFilter;
	private Iterator<PageFilter> it;
	
	public SimplePageFilterChain(List<PageFilter> filters,
			PageFilter lastFilter) {
		this.lastFilter = lastFilter;
		this.it = filters.iterator();
	}

	public RecordPage filter(RecordPage source, MigrateContext context) {
		RecordPage target = source;
		
		if (it.hasNext()) {
			PageFilter nextFilter = it.next();
			target = nextFilter.filter(target, context, this);
		} else {
			target = lastFilter.filter(target, context, this);
		}
		
		return target;
	}

}
