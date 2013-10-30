package org.yubing.datmv.type.mem.list.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 列表对象写入器
 * 
 * @author yubing
 *
 */
public class ListObjectWriter extends ArrayList<Object> implements PageWriter {

	private static final long serialVersionUID = 8240260620490379556L;
	
	protected Class<?> clazz;
	
	public ListObjectWriter(String className) {
		try {
			this.clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ListObjectWriter(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public void open(MigrateContext context) {
		// TODO 自动生成方法存根
	}
	
	public void writePage(RecordPage recPage) {
		recPage.reset();

		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			writeRecord(record);
		}
	}
	
	protected void writeRecord(Record record) {
		Object obj = ReflectUtils.newInstance(this.clazz);
		
		for (Iterator<DataField> iterator = record.iterator(); iterator.hasNext();) {
			DataField field = iterator.next();
			
			try {
				PropertyUtils.setProperty(obj, field.getName(), field.getData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.add(obj);
	}

	public void release() {
		// TODO 自动生成方法存根
	}

	/**
	 * 转换为对象列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> toList() {
		List<E> list = new ArrayList<E>();
		
		for (Iterator<Object> it = this.iterator(); it.hasNext();) {
			Object e = it.next();
			list.add((E)e);
		}
		
		return list;
	}

}
