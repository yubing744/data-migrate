package org.yubing.datmv.mapping.handler;

import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.ConfigItem;
import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MappingHandler;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordContext;
import org.yubing.datmv.type.convert.TransposePageWriter;
import org.yubing.datmv.type.mem.object.RecordPageWriter;
import org.yubing.datmv.util.RecordUtils;

/**
 *	迁移配置映射处理器
 *
 * @author Wu CongWen
 * @email yubing744@163.com
 * @date 2013-7-26
 */
public class MigrateConfigMappingHandler implements MappingHandler {

	protected boolean transpose = false;
	protected String migrateConfig;
	
	public MigrateConfigMappingHandler(String migrateConfig, Boolean transpose) {
		this.migrateConfig = migrateConfig;
		this.transpose = transpose!=null?transpose.booleanValue():false;
	}
	
	public DataField mapFrom(DataField targetField, ConfigItem configItem, RecordContext context) {
		Record source = context.getSource();
		
		DataMigrater dm = new DataMigrater(context);
		XmlMigrateConfig xmc = new XmlMigrateConfig(migrateConfig);
		
		RecordPageWriter page = new RecordPageWriter();
		
		if (transpose) {
			xmc.setTargetWriter(new TransposePageWriter(page));
		} else {
			xmc.setTargetWriter(page);
		}
		
		dm.setMigrateConfig(xmc);
		
		dm.addAttributes(RecordUtils.toMap(source));
		
		dm.migrate();
		
		targetField.setData(page);
		
		return targetField;
	}

}
