package org.yubing.datmv.config.xml.parser.argment;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.config.xml.ArgmentsParser;
import org.yubing.datmv.config.xml.MigrateTypeConfig;
import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.config.xml.parser.config.TypeTargetParser;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.util.ReflectUtils;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-19
 */
public class PageWriterArgParser extends TypeTargetParser implements
		ArgmentsParser {
	public static final String TAG_TARGET = "target";

	private PageWriter targetWriter;

	public Object[] parserArgs(XmlMigrateConfig xmlMigrateConfig,
			Element element) {
		if (element != null) {
			NodeList nodeList = element.getElementsByTagName(TAG_TARGET);
			int size = nodeList.getLength();

			if (size > 0) {
				Node node = nodeList.item(0);
				if (node instanceof Element) {
					Element ele = (Element) node;
					this.parse(xmlMigrateConfig, ele);
				}
			}
		}

		return new Object[] { targetWriter };
	}

	public void parseType(XmlMigrateConfig xmlMigrateConfig,
			MigrateTypeConfig typeConfig, Object[] args) {
		String writerClass = typeConfig.getWriter();
		if (!StringUtils.isBlank(writerClass)) {
			this.targetWriter = (PageWriter) ReflectUtils.newInstance(
					writerClass, args);
		}
	}
}
