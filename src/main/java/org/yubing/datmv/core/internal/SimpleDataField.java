package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.DataField;

public class SimpleDataField implements DataField {

	private String name;
	private String type;
	
	private Object data;

	public SimpleDataField() {
		super();
	}

	public SimpleDataField(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[name:").append(name).append(",");
		sb.append("type:").append(type).append(",");
		sb.append("data:").append(data).append("]");
		return sb.toString();
	}

}
