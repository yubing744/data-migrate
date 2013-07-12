package org.yubing.datmv.util;

import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MigrateConfig;

public class DataMigrateHelper {
	
	/**
	 * 根据配置文件创建一个数据迁移器实例
	 * 
	 * @param xmlConfig
	 * @return
	 */
	public static DataMigrater buildFrom(String xmlConfig) {
		DataMigrater dm = new DataMigrater();
		dm.setMigrateConfig(new XmlMigrateConfig(xmlConfig));
		return dm;
	}
	
	/**
	 * 根据配置文件创建一个数据迁移器实例
	 * 
	 * @param migrateConfig
	 * @return
	 */
	public static DataMigrater buildFrom(MigrateConfig migrateConfig) {
		DataMigrater dm = new DataMigrater();
		dm.setMigrateConfig(migrateConfig);
		return dm;
	}
}
