package org.yubing.datmv.filter;

import org.yubing.datmv.core.FilterChain;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-13
 */
public class HiddenFirstRecordFilter implements RecordFilter {

	public Record filter(Record source, PageContext context, FilterChain chain) {
		Integer pageNum = (Integer) context.getAttribute("cur.page.num");
		Boolean isFirst = (Boolean) context.getAttribute("first.record.flag");
		if (pageNum == 1 && isFirst == null) {
			context.setAttribute("first.record.flag", new Boolean(false));
			return null;
		}

		return chain.filter(source, context);
	}
}
