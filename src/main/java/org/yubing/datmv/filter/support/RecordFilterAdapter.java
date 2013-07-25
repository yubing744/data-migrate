package org.yubing.datmv.filter.support;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;
import org.yubing.datmv.core.RecordFilterChain;

/**
 *	过滤器适配器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-25
 */
public class RecordFilterAdapter implements RecordFilter {

	public void init(MigrateContext context) {
		// TODO Auto-generated method stub
	}

	public Record filter(Record source, PageContext context,
			RecordFilterChain chain) {
		return chain.filter(source, context);
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
