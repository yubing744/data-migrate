package org.yubing.datmv.type.empty;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.RecordPage;

public class EmptyReader implements PageReader {

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}

	public RecordPage readPage(int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

}
