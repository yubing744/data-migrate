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

	protected MigrateContext parent;
	
	private Map<String, String> paramsMap = new HashMap<String, String>();
	private Map<String, Object> attrMap = new HashMap<String, Object>();
	
	private MigrateConfig migrateConfig;

	public SimpleMigrateContext() {
		this.parent = null;
	}
	
	public SimpleMigrateContext(MigrateContext parent) {
		this.parent = parent;
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

	public Map<String, Object> getAttributeMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (this.parent != null) {
			map.putAll(this.parent.getAttributeMap());
		}
		
		map.putAll(this.attrMap);
		
		return map;
	}

	public Map<String, String> getParameterMap() {
		Map<String, String> map = new HashMap<String, String>();
		
		if (this.parent != null) {
			map.putAll(this.parent.getParameterMap());
		}
		
		map.putAll(this.paramsMap);
		
		return map;
	}
	
	public Object getAttribute(String key) {
		Object result = null;
		
		if (result == null) {
			result = attrMap.get(key);
		}
		
		if (this.parent != null) {
			result = this.parent.getAttribute(key);
		}
		
		return result;
	}


	public String getParameter(String key) {
		String result = null;
		
		if (result == null) {
			result = paramsMap.get(key);
		}
		
		if (this.parent != null) {
			result = this.parent.getParameter(key);
		}

		return result;
	}
	
	public void setParameter(String key, String value) {
		paramsMap.put(key, value);
	}

	public void setAttribute(String key, Object value) {
		attrMap.put(key, value);
	}
	
	public MigrateContext getParent() {
		return this.parent;
	}
}
