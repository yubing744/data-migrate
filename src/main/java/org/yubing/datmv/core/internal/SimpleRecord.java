package org.yubing.datmv.core.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.Record;

public class SimpleRecord implements Record {
	
	protected Map<String, DataField> fileds = new LinkedHashMap<String, DataField>();

	public SimpleRecord() {
		super();
	}

	public SimpleRecord(Record source) {
		if (source != null) {
			for (Iterator<DataField> iterator = source.iterator(); iterator.hasNext();) {
				DataField field = iterator.next();
				this.addDataField(field);
			}
		}
	}

	public Map<String, DataField> getFileds() {
		return fileds;
	}

	public void removeDataField(String key) {
		this.getFileds().remove(key);
	}

	public int getColSize() {
		return fileds.size();
	}

	public DataField getDataField(String key) {
		return this.getFileds().get(key);
	}

	public Set<String> keySet() {
		return this.getFileds().keySet();
	}

	@Override
	public String toString() {
		return this.getFileds().toString();
	}

	public Object getFieldData(String key) {
		DataField df = this.getDataField(key);
		
		if (df != null) {
			return df.getData();
		}
		
		return null;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Set<String> keys = this.keySet();
		
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			String key =  it.next();
			map.put(key, this.getFieldData(key));
		}
		
		return map;
	}

	public void addDataField(DataField dataField) {
		this.fileds.put(dataField.getName(), dataField);
	}

	public int size() {
		return this.fileds.size();
	}

	public Iterator<DataField> iterator() {
		return this.fileds.values().iterator();
	}

}
