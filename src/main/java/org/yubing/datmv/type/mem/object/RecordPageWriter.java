package org.yubing.datmv.type.mem.object;

import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleRecordPage;

/**
 *	记录页写入器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-24
 */
public class RecordPageWriter extends SimpleRecordPage implements PageWriter {

	public void open(MigrateContext ctx) {
		
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			this.writeRecord(record);
		}
	}

	public void release() {
		
	}
}
