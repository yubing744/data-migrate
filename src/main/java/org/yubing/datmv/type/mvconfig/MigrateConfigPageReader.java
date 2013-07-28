package org.yubing.datmv.type.mvconfig;

import org.yubing.datmv.config.xml.XmlMigrateConfig;
import org.yubing.datmv.core.DataMigrater;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.type.convert.ExcelNormalizePageWriter;
import org.yubing.datmv.type.excel.ExcelReader;

/**
 * 迁移配置页数据读取器
 * 
 * @author yubing
 *
 */
public class MigrateConfigPageReader implements PageReader {

	protected String migrateConfig;
	protected DataMigrater dataMigrater;
	protected ExcelReader excelReader;
	
	protected boolean hasHeader = false;
	
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
	
	public MigrateConfigPageReader(String configFile) {
		this.migrateConfig = configFile;
	}
	
	public void open(MigrateContext ctx) {
		DataMigrater dataMigrater = new DataMigrater(ctx);
		XmlMigrateConfig xmc = new XmlMigrateConfig(migrateConfig);
		
		ExcelNormalizePageWriter excelWriter = new ExcelNormalizePageWriter();
		xmc.setTargetWriter(excelWriter);
		dataMigrater.setMigrateConfig(xmc);
		dataMigrater.migrate();
		
		excelReader = new ExcelReader(excelWriter.getExcelInputStream());
		excelReader.setHasHeader(this.hasHeader);
		excelReader.open(ctx);
	}

	public boolean hasNext() {
		return excelReader.hasNext();
	}
	
	public RecordPage readPage(int pageSize) {
		return excelReader.readPage(pageSize);
	}

	public void release() {
		// TODO Auto-generated method stub
	}

}
