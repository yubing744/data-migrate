package org.yubing.datmv.core;

public interface RecordContext extends MigrateContext {
	Record getSource();

	Record getTarget();

	ConfigItem getConfigItem();

	PageContext getPageContext();
}
