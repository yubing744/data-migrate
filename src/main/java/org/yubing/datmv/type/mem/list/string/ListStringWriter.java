package org.yubing.datmv.type.mem.list.string;

import java.util.ArrayList;
import java.util.Iterator;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

/**
 * 列表字符串吸入器
 * 
 * @author yubing
 *
 */
public class ListStringWriter extends ArrayList<String> implements PageWriter {

	private static final long serialVersionUID = 7211880431416089360L;

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			Iterator<DataField> it = record.iterator();
			
			if (it.hasNext()) {
				DataField df = it.next();
				this.add(String.valueOf(df.getData()));
			}
		}
	}

	public void release() {
		// TODO Auto-generated method stub
	}
}
