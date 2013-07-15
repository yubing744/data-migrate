package org.yubing.datmv.util;

import java.util.HashMap;
import java.util.Map;

import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.type.mem.map.MapWriter;

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
	
	/**
	 * 加载Map
	 * 
	 * @param config
	 * @return
	 */
	public static<K,V> Map<K, V> loadMap(String config) {
		Map<K, V> map = new HashMap<K, V>();
		
		XmlMigrateConfig xmc = new XmlMigrateConfig(config);
		xmc.setTargetWriter(new MapWriter<K, V>(map));
		DataMigrater dm = DataMigrateHelper.buildFrom(xmc);
		dm.migrate();
		
		return map;
	}
}
