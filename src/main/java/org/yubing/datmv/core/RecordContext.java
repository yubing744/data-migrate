package org.yubing.datmv.core;

public interface RecordContext extends PageContext{
	
	Record getSource();

	Record getTarget();

	PageContext getPageContext();
}
