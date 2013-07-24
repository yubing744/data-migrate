package org.yubing.datmv.type.mem.object;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.RecordPage;

/**
 *	记录页写入器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-24
 */
public class RecordPageWriter implements PageWriter {

	private RecordPage recordPage;
	
	public RecordPage getRecordPage() {
		return recordPage;
	}

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
		
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}

	public void writePage(RecordPage pageRecord) {
		this.recordPage = pageRecord;
	}

}
