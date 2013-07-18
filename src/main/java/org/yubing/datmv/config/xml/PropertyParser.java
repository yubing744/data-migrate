package org.yubing.datmv.config.xml;

import org.w3c.dom.Element;

public interface PropertyParser {

	void parserProperties(XmlMigrateConfig xmlMigrateConfig, Object obj, Element sourceEle);

}
