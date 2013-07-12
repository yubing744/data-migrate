package org.yubing.datmv.config.xml;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-11
 */
public class MigrateListenerConfig {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 构造参数解析器
	 * 
	 */
	private String constructorArgParser = null;

	/**
	 * 监听器
	 */
	private String listener;

	public MigrateListenerConfig(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConstructorArgParser() {
		return constructorArgParser;
	}

	public void setConstructorArgParser(String constructorArgParser) {
		this.constructorArgParser = constructorArgParser;
	}

	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}

}
