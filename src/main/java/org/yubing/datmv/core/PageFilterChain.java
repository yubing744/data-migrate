package org.yubing.datmv.core;

public interface PageFilterChain {

	RecordPage filter(RecordPage source, MigrateContext context);

}
