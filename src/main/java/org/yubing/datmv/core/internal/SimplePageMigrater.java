package org.yubing.datmv.core.internal;

import java.util.Iterator;
import java.util.List;

import org.yubing.datmv.core.RecordFilterChain;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.MigrateLog;
import org.yubing.datmv.core.MigrateLogFactory;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.PageMigrater;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordFilter;
import org.yubing.datmv.core.RecordMigrater;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.util.config.ConfigUtils;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public class SimplePageMigrater implements PageMigrater, RecordFilter {

	private MigrateLog migrateLog = MigrateLogFactory.getMigrateLog();

	private RecordMigrater recordMigrater;
	private List<RecordFilter> filters;
	
	public SimplePageMigrater() {

	}

	public void setRecordMigrater(RecordMigrater recordMigrater) {
		this.recordMigrater = recordMigrater;
	}

	public void init(MigrateContext context) {
		this.recordMigrater = (RecordMigrater) ConfigUtils.newObjectFromConfig(
				"record.migrater.impl",
				"org.yubing.datmv.core.internal.SimpleRecordMigrater");
		
		MigrateConfig migrateConfig = context.getMigrateConfig();
		this.filters = migrateConfig.getRecordFilters();
		
		if (filters != null && !filters.isEmpty()) {
			for (Iterator<RecordFilter> it = filters.iterator(); it.hasNext();) {
				RecordFilter recordFilter = (RecordFilter) it.next();
				recordFilter.init(context);
			}
		}
	}

	public RecordPage migrate(RecordPage recPage, MigrateContext migrateContext) {
		RecordPage resultPage = null;
		
		if (recPage != null && recordMigrater != null) {
			resultPage = new SimpleRecordPage();
			recPage.reset();
			
			SimplePageContext context= new SimplePageContext(migrateContext, recPage, resultPage);

			while (recPage.hasNext()) {
				Record source = recPage.readRecord();
				
				updateNumber(migrateContext);
				
				Record target = filterRecord(source, context);
				
				if (target != null) {
					resultPage.writeRecord(target);
				} else {
					migrateLog.log("Filterd: " + source);
				}
			}
		}
		
		return resultPage;
	}

	protected void updateNumber(MigrateContext migrateContext) {
		Integer num = (Integer)migrateContext.getAttribute("cur.num");
		
		if (num == null) {
			migrateContext.setAttribute("cur.num", 1);
		} else {
			migrateContext.setAttribute("cur.num", num + 1);
		}
	}

	public Record filter(Record source, PageContext context, RecordFilterChain chain) {
		return recordMigrater.migrate(source, context);
	}

	protected Record filterRecord(Record source, PageContext context) {
		Record target = null;
		
		if (filters != null && !filters.isEmpty()) {
			RecordFilterChain chain = new SimpleRecordFilterChain(filters, this);
			target = chain.filter(source, context);
		} else {
			target = this.filter(source, context, null);
		}
		
		return target;
	}

	public void destroy() {
		if (filters != null && !filters.isEmpty()) {
			for (Iterator<RecordFilter> it = filters.iterator(); it.hasNext();) {
				RecordFilter recordFilter = (RecordFilter) it.next();
				recordFilter.destroy();
			}
		}
	}

}
