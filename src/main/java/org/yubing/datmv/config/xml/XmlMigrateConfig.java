package org.yubing.datmv.config.xml;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.AbstractMigrateConfig;
import org.yubing.datmv.config.xml.parser.config.ConfigItemsParser;
import org.yubing.datmv.config.xml.parser.config.MappingHandlersConfigParser;
import org.yubing.datmv.config.xml.parser.config.MigrateListenerConfigsParser;
import org.yubing.datmv.config.xml.parser.config.MigrateListenerParser;
import org.yubing.datmv.config.xml.parser.config.MigrateTypeConfigsParser;
import org.yubing.datmv.config.xml.parser.config.PagePreviewParser;
import org.yubing.datmv.config.xml.parser.config.PreviewTypeConfigsParser;
import org.yubing.datmv.config.xml.parser.config.RecordFilterConfigsParser;
import org.yubing.datmv.config.xml.parser.config.RecordFilterParser;
import org.yubing.datmv.config.xml.parser.config.TypeSourceParser;
import org.yubing.datmv.config.xml.parser.config.TypeTargetParser;
import org.yubing.datmv.util.DocumentUtils;
import org.yubing.datmv.util.ResourceUtils;

/**
 * XML格式 迁移配置实现
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class XmlMigrateConfig extends AbstractMigrateConfig {
	protected static final String DEFAULT_XML_CONFIG = "/default-migrate-cfg.xml";

	protected String xmlConfig = null;
	protected Map<String, ConfigParser> parsers;

	protected Map<String, MigrateTypeConfig> migrateTypeConfigs;
	protected Map<String, PreviewTypeConfig> previewTypeConfigs;
	protected Map<String, MappingHandlerConfig> mappingHandlerConfigs;
	protected Map<String, RecordFilterConfig> recordFilterConfigs;
	protected Map<String, MigrateListenerConfig> migrateListenerConfigs;

	public XmlMigrateConfig() {
		this.initParsers();
		this.parseInitConfig();
	}

	public XmlMigrateConfig(String xmlConfig) {
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

	protected Map<String, MigrateTypeConfig> getMigrateTypeConfigs() {
		if (migrateTypeConfigs == null) {
			migrateTypeConfigs = new HashMap<String, MigrateTypeConfig>();
		}
		return migrateTypeConfigs;
	}

	protected Map<String, PreviewTypeConfig> getPreviewTypeConfigs() {
		if (previewTypeConfigs == null) {
			previewTypeConfigs = new HashMap<String, PreviewTypeConfig>();
		}
		return previewTypeConfigs;
	}

	public Map<String, MappingHandlerConfig> getMappingHandlerConfigs() {
		if (mappingHandlerConfigs == null) {
			mappingHandlerConfigs = new HashMap<String, MappingHandlerConfig>();
		}
		return mappingHandlerConfigs;
	}

	public Map<String, RecordFilterConfig> getRecordFilterConfigs() {
		if (recordFilterConfigs == null) {
			recordFilterConfigs = new HashMap<String, RecordFilterConfig>();
		}
		return recordFilterConfigs;
	}

	public Map<String, MigrateListenerConfig> getMigrateListenerConfigs() {
		if (migrateListenerConfigs == null) {
			migrateListenerConfigs = new HashMap<String, MigrateListenerConfig>();
		}
		return migrateListenerConfigs;
	}

	/**
	 * 添加配置解析器
	 * 
	 * @param nodeName
	 * @param configParser
	 */
	public void addConfigParser(String nodeName, ConfigParser configParser) {
		getParsers().put(nodeName, configParser);
	}

	/**
	 * 添加迁移类型配置
	 * 
	 * @param nodeName
	 * @param configParser
	 */
	public void addMigrateTypeConfig(String typeName,
			MigrateTypeConfig migrateTypeConfig) {
		getMigrateTypeConfigs().put(typeName, migrateTypeConfig);
	}

	/**
	 * 添加预览类型配置
	 * 
	 * @param nodeName
	 * @param configParser
	 */
	public void addPreviewTypeConfig(String typeName,
			PreviewTypeConfig previewTypeConfig) {
		getPreviewTypeConfigs().put(typeName, previewTypeConfig);
	}

	/**
	 * 获取迁移类型配置
	 * 
	 * @param typeName
	 * @return
	 */
	public MigrateTypeConfig getMigrateTypeConfig(String typeName) {
		return getMigrateTypeConfigs().get(typeName);
	}

	/**
	 * 添加映射处理器配置
	 * 
	 * @param nodeName
	 * @param configParser
	 */
	public void addMappingHandler(String name,
			MappingHandlerConfig mappingHandlerConfig) {
		getMappingHandlerConfigs().put(name, mappingHandlerConfig);
	}

	/**
	 * 获取映射处理器配置
	 * 
	 * @param typeName
	 * @return
	 */
	public MappingHandlerConfig getMappingHandler(String name) {
		return getMappingHandlerConfigs().get(name);
	}

	/**
	 * 添加记录过滤器配置
	 * 
	 * @param nodeName
	 * @param configParser
	 */
	public void addRecordFilterConfig(String name,
			RecordFilterConfig recordFilterConfig) {
		getRecordFilterConfigs().put(name, recordFilterConfig);
	}

	/**
	 * 获取记录过滤器配置
	 * 
	 * @param typeName
	 * @return
	 */
	public RecordFilterConfig getRecordFilterConfig(String name) {
		return getRecordFilterConfigs().get(name);
	}

	/**
	 * 获取预览处理配置
	 * 
	 * @param typeName
	 * @return
	 */
	public PreviewTypeConfig getPreviewTypeConfig(String name) {
		return getPreviewTypeConfigs().get(name);
	}

	/**
	 * 添加迁移监听器配置
	 * 
	 * @param nodeName
	 * @param configParser
	 */
	public void addMigrateListenerConfig(String name,
			MigrateListenerConfig migrateListenerConfig) {
		getMigrateListenerConfigs().put(name, migrateListenerConfig);
	}

	/**
	 * 获取迁移监听器配置
	 * 
	 * @param typeName
	 * @return
	 */
	public MigrateListenerConfig getMigrateListenerConfig(String name) {
		return getMigrateListenerConfigs().get(name);
	}

	/**
	 * 初始化解析器
	 * 
	 */
	protected void initParsers() {
		getParsers().put("migrate-types", new MigrateTypeConfigsParser());
		getParsers().put("preview-types", new PreviewTypeConfigsParser());
		getParsers().put("mapping-handlers", new MappingHandlersConfigParser());
		getParsers().put("record-filters", new RecordFilterConfigsParser());
		getParsers().put("migrate-listeners",
				new MigrateListenerConfigsParser());

		getParsers().put("source", new TypeSourceParser());
		getParsers().put("target", new TypeTargetParser());
		getParsers().put("preview", new PagePreviewParser());
		getParsers().put("config-items", new ConfigItemsParser());

		getParsers().put("filter", new RecordFilterParser());
		getParsers().put("listener", new MigrateListenerParser());
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
			Document doc = DocumentUtils.parseStream(is);
			parseConfig(doc);
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
}
