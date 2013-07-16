package org.yubing.datmv.core.internal;

import java.util.List;

import org.yubing.datmv.core.FilterChain;
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

	public SimplePageMigrater() {
		init();
	}

	public void setRecordMigrater(RecordMigrater recordMigrater) {
		this.recordMigrater = recordMigrater;
	}

	public void init() {
		this.recordMigrater = (RecordMigrater) ConfigUtils.newObjectFromConfig(
				"record.migrater.impl",
				"org.yubing.datmv.core.internal.SimpleRecordMigrater");

	}

	public RecordPage migrate(RecordPage recPage, MigrateContext migrateContext) {
		RecordPage resultPage = null;
		
		if (recPage != null && recordMigrater != null) {
			resultPage = new SimpleRecordPage();
			recPage.reset();
			
			SimplePageContext context= new SimplePageContext(recPage, resultPage);
			context.setMigrateContext(migrateContext);
			
			while (recPage.hasNext()) {
				Record source = recPage.readRecord();
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

	public Record filter(Record source, PageContext context, FilterChain chain) {
		return recordMigrater.migrate(source, context);
	}

	protected Record filterRecord(Record source, PageContext context) {
		MigrateConfig migrateConfig = context.getMigrateConfig();
		List<RecordFilter> filters = migrateConfig.getRecordFilters();
		
		if (filters != null && !filters.isEmpty()) {
			FilterChain chain = new SimpleFilterChain(filters, this);
			source = chain.filter(source, context);
		} else {
			source = this.filter(source, context, null);
		}
		
		return source;
	}

}
