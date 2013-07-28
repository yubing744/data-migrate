package org.yubing.datmv.core;

public interface PageContext extends MigrateContext {
	
	RecordPage getSourcePage();

	RecordPage getTargetPage();

	MigrateContext getMigrateContext();
}
