package org.yubing.datmv.core.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.Record;

public class SimpleRecord implements Record {
	private Map<String, DataField> fileds = new LinkedHashMap<String, DataField>();

	public Map<String, DataField> getFileds() {
		return fileds;
	}

	public void addDataField(String key, DataField dataField) {
		this.getFileds().put(key, dataField);
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

}
