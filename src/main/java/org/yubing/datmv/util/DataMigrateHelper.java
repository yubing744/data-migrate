package org.yubing.datmv.util;

import java.util.List;
import java.util.Map;

import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MigrateConfig;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.type.excel.ExcelWriter;
import org.yubing.datmv.type.mem.list.map.ListMapWriter;
import org.yubing.datmv.type.mem.list.object.ListObjectReader;
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
		MapWriter<K, V> map = new MapWriter<K, V>();
		
		DataMigrater dm = new DataMigrater();
		XmlMigrateConfig xmc = new XmlMigrateConfig(config);
		dm.setMigrateConfig(xmc);
		xmc.setTargetWriter(map);
		dm.migrate();
		
		return map;
	}

	/**
	 * 加载表数据
	 * 
	 * @param config
	 * @return
	 */
	public static List<Map<String, Object>> loadListMap(String config, Map<String, String> params) {
		ListMapWriter writer = new ListMapWriter();
		
		DataMigrater dm = new DataMigrater();
		dm.addParameters(params);
		XmlMigrateConfig xmc = new XmlMigrateConfig(config);
		dm.setMigrateConfig(xmc);
		xmc.setTargetWriter(writer);
		dm.migrate();
		
		return writer;
	}
	
	/**
	 * 加载列表Map
	 * 
	 * @param config
	 * @return
	 */
	public static List<Map<String, Object>> loadListMap(String config) {
		return loadListMap(config, null);
	}

	/**
	 * 执行迁移
	 * 
	 * @param config
	 */
	public static void migrate(String config) {
		DataMigrater dm = new DataMigrater();
		XmlMigrateConfig xmc = new XmlMigrateConfig(config);
		dm.setMigrateConfig(xmc);
		dm.migrate();
	}
	
	/**
	 * 执行预览
	 * 
	 * @param config
	 */
	public static void priview(String config) {
		DataMigrater dm = new DataMigrater();
		XmlMigrateConfig xmc = new XmlMigrateConfig(config);
		dm.setMigrateConfig(xmc);
		dm.preview();
	}

	/**
	 * 写入
	 * 
	 * @param reader
	 * @param writer
	 */
	public static void writeTo(PageReader reader, PageWriter writer) {
		DataMigrater dm = new DataMigrater();
		XmlMigrateConfig xmc = new XmlMigrateConfig();
		
		xmc.setSourceReader(reader);
		xmc.setTargetWriter(writer);
		
		dm.setMigrateConfig(xmc);
		dm.migrate();
	}
}
