package org.yubing.datmv.core;

public interface PageContext extends MigrateContext {
	
	RecordPage getSource();

	RecordPage getTarget();

	MigrateContext getMigrateContext();
}
