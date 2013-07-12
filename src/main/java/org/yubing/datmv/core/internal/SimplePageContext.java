package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.RecordPage;

public class SimplePageContext extends SimpleMigrateContext implements
		PageContext {

	private RecordPage source;
	private RecordPage target;

	private MigrateContext migrateContext;

	public SimplePageContext() {
		super();
	}

	public SimplePageContext(RecordPage source, RecordPage target) {
		super();
		this.source = source;
		this.target = target;
	}

	public SimplePageContext(RecordPage source, RecordPage target,
			MigrateContext migrateContext) {
		super();
		this.source = source;
		this.target = target;
		this.migrateContext = migrateContext;
	}

	public void setSource(RecordPage source) {
		this.source = source;
	}

	public void setTarget(RecordPage target) {
		this.target = target;
	}

	public RecordPage getSource() {
		return source;
	}

	public RecordPage getTarget() {
		return target;
	}

	public MigrateContext getMigrateContext() {
		return migrateContext;
	}

	public void setMigrateContext(MigrateContext migrateContext) {
		this.migrateContext = migrateContext;
	}

	public MigrateConfig getMigrateConfig() {
		return this.migrateContext.getMigrateConfig();
	}

	public void setMigrateConfig(MigrateConfig migrateConfig) {
		this.migrateContext.setMigrateConfig(migrateConfig);
	}

	public Object getAttribute(String key) {
		Object val = getAttrMap().get(key);
		if (val == null) {
			val = this.migrateContext.getAttribute(key);
		}
		return val;
	}
}
