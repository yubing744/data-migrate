package org.yubing.datmv.util.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 全局配置
 * 
 * @author oojdon
 * 
 */

public class Configuration {

	private static Configuration gConfig = null;
	private static AtomicBoolean initialized = new AtomicBoolean();

	private Configuration() {
		
	}

	public static Configuration getInstance() {
		if (initialized.compareAndSet(false, true)) {
			gConfig = new Configuration();
			gConfig.initialize();
		}
		
		return gConfig;
	}

	public static void putValue(String key, String value) {
		getInstance().configMap.put(key, value);
	}

	public static Map<String, String> rawMap() {
		return getInstance().configMap;
	}

	private void initialize() {
		configMap = new HashMap<String, String>();
	}

	private Map<String, String> configMap = null;

	/**
	 * 通过 key来访问字符串值
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return getInstance().configMap.get(key);
	}

	/**
	 * 通过key来访问 整数值
	 * 
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key) {
		return getIntValue(key, 0);
	}

	/**
	 * 通过 key来访问 long类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static long getLongValue(String key) {
		return getLongValue(key, 0);
	}

	/**
	 * 通过 key来访问 float类型的值
	 * @param key
	 * @return
	 */
	public static float getFloatValue(String key) {
		return getFloatValue(key, 0.0f);
	}

	public static double getDoubleValue(String key) {
		return getDoubleValue(key, 0.0);
	}

	public static boolean getBooleanValue(String key) {
		return getBooleanValue(key, false);
	}

	/**
	 * 通过key来访问字符串的值，如果不存在则用默认值。
	 * 
	 * @param key
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		String value = getValue(key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * 通过key来访问整数型的值，如果不存在则用默认值。
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIntValue(String key, int defaultValue) {
		String value = getValue(key);
		if (value != null) {
			return NumberUtils.toInt(value);
		}

		return defaultValue;
	}

	public static float getFloatValue(String key, float defaultValue) {
		String value = getValue(key);
		if (value != null) {
			return NumberUtils.toFloat(value);
		}
		return defaultValue;
	}

	public static double getDoubleValue(String key, double defaultValue) {
		String value = getValue(key);
		if (value != null) {
			return NumberUtils.toDouble(value);
		}
		return defaultValue;
	}

	public static long getLongValue(String key, long defaultValue) {
		String value = getValue(key);
		if (value != null) {
			return NumberUtils.toLong(value);
		}

		return defaultValue;
	}

	public static boolean getBooleanValue(String key, boolean defaultValue) {
		String value = getValue(key);
		if (value != null) {
			return "true".equals(value);
		}

		return defaultValue;
	}

}
