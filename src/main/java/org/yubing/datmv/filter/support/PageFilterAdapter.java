package org.yubing.datmv.filter.support;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageFilter;
import org.yubing.datmv.core.PageFilterChain;
import org.yubing.datmv.core.RecordPage;

/**
 *	页过滤器适配器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-25
 */
public class PageFilterAdapter implements PageFilter {

	public void init(MigrateContext context) {
		// TODO Auto-generated method stub
	}

	public RecordPage filter(RecordPage source, MigrateContext context,
			PageFilterChain chain) {
		return chain.filter(source, context);
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
