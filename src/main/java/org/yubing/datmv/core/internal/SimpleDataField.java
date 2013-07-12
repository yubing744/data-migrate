package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.DataField;

public class SimpleDataField implements DataField {

	private String name;
	private String type;
	private String data;

	public SimpleDataField() {
		super();
	}

	public SimpleDataField(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
