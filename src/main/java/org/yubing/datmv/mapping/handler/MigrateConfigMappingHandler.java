package org.yubing.datmv.mapping.handler;

import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordContext;
import org.yubing.datmv.type.mem.object.RecordPageWriter;

/**
 *	迁移配置映射处理器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-26
 */
public class MigrateConfigMappingHandler implements MappingHandler {

	private String migrateConfig;
	
	public MigrateConfigMappingHandler(String migrateConfig) {
		this.migrateConfig = migrateConfig;
	}
	
	public DataField mapFrom(DataField targetField, RecordContext context) {
		Record source = context.getSource();
		
		DataMigrater dm = new DataMigrater(context);
		XmlMigrateConfig xmc = new XmlMigrateConfig(migrateConfig);
		
		RecordPageWriter page = new RecordPageWriter();
		xmc.setTargetWriter(page);
		dm.setMigrateConfig(xmc);
		
		dm.addAttributes(source.toMap());
		
		dm.migrate();
		
		targetField.setData(page);
		
		return targetField;
	}

}
