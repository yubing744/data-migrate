package org.yubing.datmv.util;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * W3C DOM Utils
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class DocumentUtils {

	protected static DocumentBuilder newDomBuilder()
			throws ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		return docBuilder;
	}

	/**
	 * 解析XML字符串为 DOM
	 * 
	 * @param xml
	 * @return
	 */
	public static Document parseXml(String xml) {
		try {
			DocumentBuilder docBuilder = newDomBuilder();
			return docBuilder.parse(new InputSource(new StringReader(xml)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解析XML文件为 DOM
	 * 
	 * @param xmlFile
	 *            文件名
	 * @return
	 */
	public static Document parseFile(String xmlFile) {
		try {
			DocumentBuilder docBuilder = newDomBuilder();
			return docBuilder.parse(new InputSource(new FileReader(xmlFile)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解析XML文件为 DOM
	 * 
	 * @param file
	 * @return
	 */
	public static Document parseFile(File file) {
		try {
			DocumentBuilder docBuilder = newDomBuilder();
			return docBuilder.parse(new InputSource(new FileReader(file)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解析输入流为 DOM
	 * 
	 * @param is
	 * @return
	 */
	public static Document parseStream(InputStream is) {
		try {
			DocumentBuilder docBuilder = newDomBuilder();
			return docBuilder.parse(new InputSource(is));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 根据 Tag 查找唯一Element.
	 * 
	 * @param parent
	 * @param tagName
	 * @return
	 */
	public static Element findOneElementByTagName(Element parent, String tagName) {
		NodeList nodeList = parent.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			return (Element) nodeList.item(0);
		}
		return null;
	}

	/**
	 * 查找Tag的属性
	 * 
	 * @param ele
	 * @param attrName
	 * @return
	 */
	public static String findAttrByName(Element ele, String attrName) {
		String attrVal = ele.getAttribute(attrName);
		if (attrVal == null || attrVal.trim().equals("")) {
			Element attrEle = findOneElementByTagName(ele, attrName);
			if (attrEle != null) {
				attrVal = attrEle.getAttribute("value");
			}
		}
		return attrVal;
	}
}
