package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.RecordPage;

/**
 * 简单页面上下文
 * 
 * @author yubing
 *
 */
public class SimplePageContext extends SimpleMigrateContext implements PageContext {

	private RecordPage source;
	private RecordPage target;

	private MigrateContext migrateContext;

	public SimplePageContext(MigrateContext migrateContext, RecordPage source, RecordPage target) {
		super(migrateContext);
		
		this.source = source;
		this.target = target;
		this.migrateContext = migrateContext;
	}

	public RecordPage getSourcePage() {
		return source;
	}

	public RecordPage getTargetPage() {
		return target;
	}

	public MigrateContext getMigrateContext() {
		return migrateContext;
	}

	public MigrateConfig getMigrateConfig() {
		return this.migrateContext.getMigrateConfig();
	}
}
