package org.yubing.datmv.core;

/**
 * 数据迁移数据类型定义
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class DataType {
	public static final String STRING = "string";
	
	public static final String SHORT = "short";
	public static final String INTEGER = "int";
	public static final String LONG = "long";
	
	public static final String FLOAT = "float";
	public static final String DOUBLE = "double";
	
	public static final String CHARACTER = "char";
	public static final String BOOLEAN = "bool";
	
	public static final String DATE = "date";
	
	public static final String OBJECT = "object";

	
	public static final String[] NUMBER = new String[]{INTEGER, FLOAT, SHORT, LONG, DOUBLE};
	
	/**
	 * 判断该类型是否为数字
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isNumber(String type) {
		if (STRING.equals(type)) {
			return false;
		}
		
		for (int i = 0; i < NUMBER.length; i++) {
			if (NUMBER[i].equals(type)) {
				return true;
			}
		}
		
		return false;
	}
}
