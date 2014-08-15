package org.yubing.datmv.type.mem.list.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

/**
 * 列表记录写入器
 * 
 * @author yubing
 *
 */
public class ListRecordWriter extends ArrayList<Record> implements PageWriter {

	private static final long serialVersionUID = -3336237452354532878L;

	public ListRecordWriter() {
		super();
	}

	public ListRecordWriter(Collection<? extends Record> c) {
		super(c);
	}

	public ListRecordWriter(int initialCapacity) {
		super(initialCapacity);
	}

	public void open(MigrateContext ctx) {
		// TODO Auto-generated method stub
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			this.add(record);
		}
	}
	
	public void release() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 转换为列表Map
	 * 
	 * @return
	 */
	public List<Map<String, Object>> toListMap() {
		List<Map<String, Object>> output = new ArrayList<Map<String, Object>>();
		
		for (Iterator<Record> it = this.iterator(); it.hasNext();) {
			Record record = (Record) it.next();
			output.add(record.toMap());
		}
		
		return output;
	}

}
