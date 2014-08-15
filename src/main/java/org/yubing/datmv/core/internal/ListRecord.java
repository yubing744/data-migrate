package org.yubing.datmv.core.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.Record;

/**
 * 列表记录
 * 
 * @author yubing
 *
 */
public class ListRecord extends LinkedList<DataField> implements Record {

	private static final long serialVersionUID = -3715634987987898842L;

	public void addDataField(DataField dataField) {
		this.add(dataField);
	}

	public void removeDataField(String name) {
		for (Iterator<DataField> it = this.iterator(); it.hasNext();) {
			DataField df = (DataField) it.next();
			if (df.getName()!= null && df.getName().equals(name)) {
				it.remove();
			}
		}
	}

	public DataField getDataField(String name) {
		for (Iterator<DataField> it = this.iterator(); it.hasNext();) {
			DataField df = (DataField) it.next();
			if (df.getName()!= null && df.getName().equals(name)) {
				return df;
			}
		}
		
		return null;
	}

	public Object getFieldData(String name) {
		DataField df = this.getDataField(name);
		if (df != null) {
			return df.getData();
		}
		
		return null;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (Iterator<DataField> iterator = this.iterator(); iterator.hasNext();) {
			DataField df = (DataField) iterator.next();
			map.put(df.getName(), df.getData());
		}
		
		return map;
	}
}
