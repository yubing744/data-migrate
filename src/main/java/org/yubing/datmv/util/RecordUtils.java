package org.yubing.datmv.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.Record;

/**
 * 迁移记录工具
 * 
 * @author yubing
 *
 */
public class RecordUtils {
	
	public static Map<String, Object> toMap(Record record) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
			DataField field =  it.next();
			map.put(field.getName(), field.getData());
		}
		
		return map;
	}
}
