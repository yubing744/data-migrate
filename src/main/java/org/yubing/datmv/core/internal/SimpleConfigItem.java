package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.MappingHandler;

/**
 *	简单配置项
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-15
 */
public class SimpleConfigItem implements ConfigItem {
	private String name;
	private String type;
	
	private String mappingKey;
	
	private MappingHandler mappingHandler;
	
	private String value;

	public SimpleConfigItem() {
	}

	public SimpleConfigItem(String name) {
		this.name = name;
	}

	public MappingHandler getMappingHandler() {
		return mappingHandler;
	}

	public String getMappingKey() {
		return mappingKey;
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

	public void setMappingKey(String mappingKey) {
		this.mappingKey = mappingKey;
	}

	public void setMappingHandler(MappingHandler mappingHandler) {
		this.mappingHandler = mappingHandler;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[name:").append(name).append(",");
		sb.append("type:").append(type).append(",");
		sb.append("mappingKey:").append(mappingKey).append("]");
		return sb.toString();
	}

	public String getDefaultValue() {
		return this.value;
	}
}
