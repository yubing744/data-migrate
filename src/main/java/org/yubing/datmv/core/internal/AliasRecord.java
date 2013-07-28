package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.DataField;

/**
 * 别名记录，当添加名称一样的数据域值，将修改数据域的名称，通过添加"!"
 * 
 * @author yubing
 *
 */
public class AliasRecord extends SimpleRecord {

	public void addDataField(DataField dataField) {
		DataField old = this.fileds.get(dataField.getName());
		
		if (old == null) {
			this.fileds.put(dataField.getName(), dataField);
		} else {
			SimpleDataField sdf = new SimpleDataField(dataField.getName() + "!", dataField.getType());
			sdf.setData(dataField.getData());
			this.addDataField(sdf);
		}
	}
	
}
