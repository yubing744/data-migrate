package org.yubing.datmv.type.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.SimpleDataField;
import org.yubing.datmv.core.internal.SimpleRecord;
import org.yubing.datmv.core.internal.SimpleRecordPage;
import org.yubing.datmv.util.ResourceUtils;

/**
 * Excel 数据记录读取器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class ExcelReader implements PageReader {
	private Workbook book;
	private Sheet sheet;
	private int curLine = 0;
	private int colSize;
	private int totalLine;
	private String excelPath;

	private boolean hasHeader = false;
	private String[] headers;
	
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
	
	public ExcelReader(String filePath) {
		this.excelPath = filePath;
	}

	public void open(MigrateContext context) {
		try {
			book = Workbook.getWorkbook(ResourceUtils.openResource(excelPath));
			sheet = book.getSheet(0);

			this.totalLine = sheet.getRows();
			this.colSize = sheet.getColumns();
			
			if (this.hasHeader) {
				this.headers = readLineDatas();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + excelPath, e);
		}
	}

	

	public boolean hasNext() {
		return this.curLine < this.totalLine - 1;
	}

	private String[] readLineDatas() {
		int colSize = this.colSize;
		int curLine = this.curLine;
		
		int startLine = curLine;
		
		String[] headers = new String[colSize];
		
		for (int c = 0; c < colSize; c++) {
			Cell cell = sheet.getCell(c, startLine);
			String cellContents = cell.getContents();
			headers[c] = cellContents;
		}
		
		this.curLine++;
		
		return headers;
	}
	
	public RecordPage readPage(int pageSize) {
		int colSize = this.colSize;
		int curLine = this.curLine;
		int totalLine = this.totalLine;

		int startLine = curLine;
		int endLine = curLine + pageSize;

		RecordPage page = new SimpleRecordPage(pageSize);

		for (int readLine = startLine; readLine < endLine
				&& readLine < totalLine; readLine++) {
			Record record = new SimpleRecord();

			for (int c = 0; c < colSize; c++) {
				Cell cell = sheet.getCell(c, readLine);
				String cellContents = cell.getContents();
				
				String key = String.valueOf(c);
				if (this.hasHeader) {
					key = this.headers[c];
				}
				
				SimpleDataField cellData = new SimpleDataField(key, DataType.STRING);
				cellData.setData(cellContents);

				record.addDataField(cellData.getName(), cellData);
			}

			page.writeRecord(record);
		}

		this.curLine = Math.min(endLine, totalLine);
		return page;
	}

	public void release() {
		if (book != null) {
			book.close();
		}
	}
}
