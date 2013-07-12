package org.yubing.datmv.config.xml;

/**
 * 迁移类型， 如 excel, csv, jdbc
 * 
 * Author: Wu Cong-Wen Date: 2011-7-10
 */
public class MigrateTypeConfig {
	/**
	 * 类型名称
	 */
	private String name;

	/**
	 * 该类型的读取器
	 */
	private String reader;

	/**
	 * 该类型写入器
	 */
	private String writer;

	/**
	 * 读取器、写入器构造参数解析器
	 * 
	 */
	private String constructorArgParser = null;

	public MigrateTypeConfig(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getConstructorArgParser() {
		return constructorArgParser;
	}

	public void setConstructorArgParser(String constructorArgParser) {
		this.constructorArgParser = constructorArgParser;
	}
}
