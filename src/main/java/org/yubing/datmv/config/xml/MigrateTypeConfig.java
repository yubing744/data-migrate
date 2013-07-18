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
	
	private String readerConstructorArgParser = null;
	
	private String writerConstructorArgParser = null;
	
	private String propertyParser = null;

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

	
	public String getReaderConstructorArgParser() {
		return readerConstructorArgParser;
	}

	public void setReaderConstructorArgParser(String readerConstructorArgParser) {
		this.readerConstructorArgParser = readerConstructorArgParser;
	}

	public String getWriterConstructorArgParser() {
		return writerConstructorArgParser;
	}

	public void setWriterConstructorArgParser(String writerConstructorArgParser) {
		this.writerConstructorArgParser = writerConstructorArgParser;
	}

	public String getConstructorArgParser() {
		return constructorArgParser;
	}

	public void setConstructorArgParser(String constructorArgParser) {
		this.constructorArgParser = constructorArgParser;
	}

	public void setPropertyParser(String propertyParser) {
		this.propertyParser = propertyParser;
	}

	public String getPropertyParser() {
		return propertyParser;
	}
	
}
