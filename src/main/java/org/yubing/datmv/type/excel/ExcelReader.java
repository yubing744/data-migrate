package org.yubing.datmv.type.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

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

	public ExcelReader(String filePath) {
		this.excelPath = filePath;
	}

	public void open() {
		try {
			book = Workbook.getWorkbook(ResourceUtils.openResource(excelPath));
			sheet = book.getSheet(0);

			this.totalLine = sheet.getRows();
			this.colSize = sheet.getColumns();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + excelPath, e);
		}
	}

	public boolean hasNext() {
		return this.curLine < this.totalLine - 1;
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
				SimpleDataField cellData = new SimpleDataField();
				String key = "" + c;
				cellData.setName("" + c);
				cellData.setType(cell.getType().toString());
				cellData.setData(cellContents);

				record.addDataField(key, cellData);
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
