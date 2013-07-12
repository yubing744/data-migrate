package org.yubing.datmv.core.internal;

import java.util.HashMap;
import java.util.Map;

import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.MigrateContext;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public class SimpleMigrateContext implements MigrateContext {

	private Map<String, Object> attrMap;
	private MigrateConfig migrateConfig;

	protected Map<String, Object> getAttrMap() {
		if (attrMap == null) {
			attrMap = new HashMap<String, Object>();
		}
		return attrMap;
	}

	public MigrateConfig getMigrateConfig() {
		return migrateConfig;
	}

	public void setMigrateConfig(MigrateConfig migrateConfig) {
		if (this.migrateConfig == null) {
			this.migrateConfig = migrateConfig;
		} else {
			throw new RuntimeException(
					"you can not modify the migrate config setting.");
		}
	}

	public Object getAttribute(String key) {
		return getAttrMap().get(key);
	}

	public void setAttribute(String key, Object value) {
		getAttrMap().put(key, value);
	}
}
