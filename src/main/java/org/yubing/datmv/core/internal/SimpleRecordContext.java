package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.PageContext;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordContext;

public class SimpleRecordContext extends SimpleMigrateContext implements
		RecordContext {
	private Record source;
	private Record target;

	private ConfigItem configItem;
	private PageContext pageContext;

	public SimpleRecordContext() {
		super();
	}

	public SimpleRecordContext(Record source, Record target) {
		super();
		this.source = source;
		this.target = target;
	}

	public SimpleRecordContext(Record source, Record target,
			ConfigItem configItem) {
		super();
		this.source = source;
		this.target = target;
		this.configItem = configItem;
	}

	public Record getSource() {
		return source;
	}

	public void setSource(Record source) {
		this.source = source;
	}

	public Record getTarget() {
		return target;
	}

	public void setTarget(Record target) {
		this.target = target;
	}

	public ConfigItem getConfigItem() {
		return configItem;
	}

	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public MigrateConfig getMigrateConfig() {
		return this.pageContext.getMigrateConfig();
	}

	public void setMigrateConfig(MigrateConfig migrateConfig) {
		this.pageContext.setMigrateConfig(migrateConfig);
	}

	public Object getAttribute(String key) {
		Object val = super.getAttribute(key);
		
		if (val == null && this.pageContext != null) {
			val = this.pageContext.getAttribute(key);
		}
		
		return val;
	}
}
