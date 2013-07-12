package org.yubing.datmv.config.xml;

/**
 * 描述
 *
 * Author: Wu Cong-Wen
 * Date: 2011-7-10
 */
public class MappingHandlerConfig {
	/**
	 * 映射处理名称
	 */
	private String name;

	/**
	 * 处理类
	 */
	private String handler;

	/**
	 * 构造参数解析器
	 * 
	 */
	private String constructorArgParser = null;

	public MappingHandlerConfig(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getConstructorArgParser() {
		return constructorArgParser;
	}

	public void setConstructorArgParser(String constructorArgParser) {
		this.constructorArgParser = constructorArgParser;
	}
}
