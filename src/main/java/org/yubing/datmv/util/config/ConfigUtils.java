package org.yubing.datmv.util.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yubing.datmv.util.ReflectUtils;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-12
 */
public class ConfigUtils {
	
	/**
	 * 根据配置文件中的类定义创建实例
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public static Object newObjectFromConfig(String key, String defaultVal) {
		String rcImplClazz = Configuration.getValue(key, defaultVal);
		return ReflectUtils.newInstance(rcImplClazz);
	}
	
	/**
	 * 处理对配置中的引用
	 * 
	 * @param value
	 * @return
	 */
	public static String handleRefVal(String value) {
		String captureKeyAndConstraint = "\\$\\{([a-zA-Z_0-9.]+)\\}";

		Matcher matcher = Pattern.compile(captureKeyAndConstraint).matcher(
				value);
		while (matcher.find()) {
			String val = matcher.group(1);
			String replaceVal = Configuration.getValue(val);
			if (replaceVal != null) {
				replaceVal = replaceVal.replaceAll("\\\\", "\\/");
				if (replaceVal != null) {
					value = value.replaceFirst("\\$\\{" + val + "\\}", replaceVal);
				}
			} else {
				throw new RuntimeException("ref for ${" + val + "} not define!");
			}
		}

		return value;
	}
	
}
