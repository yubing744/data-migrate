package org.yubing.datmv.config.xml.parser.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.yubing.datmv.util.DocumentUtils;

public class MapParser {

	public static final String TAG_ENTRY = "entry";
	
	public static final String ATTR_FROM = "from";
	public static final String ATTR_TO = "to";
	
	public Map<String, String> parse(Element mapEle) {
		Map<String, String> map = new HashMap<String, String>();
		
		NodeList itemsList = mapEle.getElementsByTagName(TAG_ENTRY);
		int size = itemsList.getLength();

		for (int i = 0; i < size; i++) {
			Node node = itemsList.item(i);
			if (node instanceof Element) {
				Element itemEle = (Element) node;
				parseEntry(map, itemEle);
			}
		}
		
		return map;
	}

	private void parseEntry(Map<String, String> map, Element ele) {
		String key = DocumentUtils.findAttrByName(ele, ATTR_FROM);
		String value = DocumentUtils.findAttrByName(ele, ATTR_TO);
		
		if (!StringUtils.isBlank(key)) {
			map.put(key, value);
		}
	}

}
