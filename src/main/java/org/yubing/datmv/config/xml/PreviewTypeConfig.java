package org.yubing.datmv.config.xml;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-19
 */
public class PreviewTypeConfig {

	/**
	 * 类型名称
	 */
	private String name;

	/**
	 * 预览器
	 */
	private String pagePreview;

	/**
	 * 读取器、写入器构造参数解析器
	 * 
	 */
	private String constructorArgParser = null;

	public PreviewTypeConfig(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPagePreview() {
		return pagePreview;
	}

	public void setPagePreview(String pagePreview) {
		this.pagePreview = pagePreview;
	}

	public String getConstructorArgParser() {
		return constructorArgParser;
	}

	public void setConstructorArgParser(String constructorArgParser) {
		this.constructorArgParser = constructorArgParser;
	}
}
