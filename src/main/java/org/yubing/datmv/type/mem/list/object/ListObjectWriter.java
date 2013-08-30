package org.yubing.datmv.type.mem.list.object;

import java.util.ArrayList;
import java.util.Iterator;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

/**
 * 列表对象写入器
 * 
 * @author yubing
 *
 */
public class ListObjectWriter extends ArrayList<Object> implements PageWriter {

	private static final long serialVersionUID = 8240260620490379556L;

	public void open(MigrateContext context) {
		// TODO 自动生成方法存根
	}
	
	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			Iterator<DataField> it = record.iterator();
			
			if (it.hasNext()) {
				DataField df = it.next();
				this.add(df.getData());
			}
		}
	}
	
	public void release() {
		// TODO 自动生成方法存根
	}

}
