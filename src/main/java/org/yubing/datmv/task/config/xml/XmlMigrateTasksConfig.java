package org.yubing.datmv.task.config.xml;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.task.MigrateTask;
import org.yubing.datmv.task.config.AbstractMigrateTasksConfig;
import org.yubing.datmv.task.config.xml.parser.TaskConfigParser;
import org.yubing.datmv.util.DocumentUtils;
import org.yubing.datmv.util.ResourceUtils;

public class XmlMigrateTasksConfig extends AbstractMigrateTasksConfig {
	protected static final String DEFAULT_XML_CONFIG = "/default-migrate-tasks-cfg.xml";

	protected String xmlConfig = null;
	protected Map<String, ConfigParser> parsers;

	private List<MigrateTask> migrateTasks = new ArrayList<MigrateTask>();
	
	public List<MigrateTask> getMigrateTasks() {
		return migrateTasks;
	}

	public void setMigrateTasks(List<MigrateTask> migrateTasks) {
		this.migrateTasks = migrateTasks;
	}

	public XmlMigrateTasksConfig() {
		this.initParsers();
		this.parseInitConfig();
	}

	public XmlMigrateTasksConfig(String xmlConfig) {
		this.xmlConfig = xmlConfig;
		this.initParsers();
		this.parseInitConfig();
	}

	public void setXmlConfig(String xmlConfig) {
		this.xmlConfig = xmlConfig;
	}

	protected Map<String, ConfigParser> getParsers() {
		if (parsers == null) {
			parsers = new HashMap<String, ConfigParser>();
		}
		
		return parsers;
	}


	/**
	 * 初始化解析器
	 * 
	 */
	protected void initParsers() {
		getParsers().put("migrate-task", new TaskConfigParser());
	}

	/**
	 * 解析初始配置
	 */
	protected void parseInitConfig() {
		parseConfigFromClassPath(DEFAULT_XML_CONFIG);
		parseXmlConfig(xmlConfig);
	}

	/**
	 * 解析XML配置，可为 file 也可为classpath
	 * 
	 * 如果是 classpath, 需要在路径前面添加 classpath:
	 * 
	 * @param xmlConfig
	 */
	public void parseXmlConfig(String xmlConfig) {
		if (!StringUtils.isBlank(xmlConfig)) {
			InputStream is = ResourceUtils.openResource(xmlConfig);
			if (is != null) {
				Document doc = DocumentUtils.parseStream(is);
				parseConfig(doc);
			} else {
				throw new RuntimeException(xmlConfig + " not found!");
			}
		}
	}

	/**
	 * 解析类路径中的XML配置
	 * 
	 * @param classpath
	 */
	public void parseConfigFromClassPath(String classpath) {
		if (!StringUtils.isBlank(classpath)) {
			InputStream is = this.getClass().getResourceAsStream(classpath);
			Document doc = DocumentUtils.parseStream(is);
			parseConfig(doc);
		}
	}

	/**
	 * 解析XML文件中的配置
	 * 
	 * @param file
	 */
	public void parseConfigFromFile(File file) {
		if (file != null) {
			Document doc = DocumentUtils.parseFile(file);
			parseConfig(doc);
		}
	}

	/**
	 * 解析XML字符串中的配置
	 * 
	 * @param xml
	 */
	public void parseConfigFromString(String xml) {
		if (!StringUtils.isBlank(xml)) {
			Document doc = DocumentUtils.parseXml(xml);
			parseConfig(doc);
		}
	}

	/**
	 * 解析 W3C DOM 中的配置
	 * 
	 * @param doc
	 */
	public void parseConfig(Document doc) {
		Element root = doc.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		int size = nodeList.getLength();

		for (int i = 0; i < size; i++) {
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				String nodeName = node.getNodeName();
				ConfigParser parser = parsers.get(nodeName);

				if (parser != null) {
					parser.parse(this, ele);
				} else {
					throw new RuntimeException("can't found parser for node:"
							+ nodeName);
				}
			}
		}
	}

	public MigrateTask getTargetTask() {
		return new MigrateTask(".", this.migrateTasks);
	}
}
