package org.yubing.datmv.type.excel;

import java.io.InputStream;
import java.text.SimpleDateFormat;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
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
	protected Workbook book;
	protected Sheet sheet;

	protected String excelPath;
	protected InputStream is;
	protected int sheetIndex = 0;
	
	protected Integer startRow = null;
	protected Integer endRow = null;
	protected Integer startColumn = null;
	protected Integer endColumn = null;
	
	protected int curRow = 0;
	
	protected boolean hasHeader = false;
	protected String[] headers;
	
	
	
	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Integer getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(Integer startColumn) {
		this.startColumn = startColumn;
	}

	public Integer getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(Integer endColumn) {
		this.endColumn = endColumn;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}
	
	public ExcelReader(String filePath) {
		this.excelPath = filePath;
		this.is = ResourceUtils.openResource(excelPath);
		this.sheetIndex = 0;
	}

	public ExcelReader(String filePath, int sheetIndex) {
		this.excelPath = filePath;
		this.is = ResourceUtils.openResource(excelPath);
		this.sheetIndex = sheetIndex;
	}
	
	public ExcelReader(InputStream is) {
		this.is = is;
		this.sheetIndex = 0;
	}

	public ExcelReader(InputStream is, int sheetIndex) {
		this.is = is;
		this.sheetIndex = sheetIndex;
	}
	
	public void open(MigrateContext context) {
		try {
			book = Workbook.getWorkbook(this.is);
			sheet = book.getSheet(sheetIndex);

			if (startRow == null) {
				this.startRow = 0;
			} 
			
			if (endRow == null) {
				this.endRow = sheet.getRows();
			} 
			
			if (startColumn == null) {
				this.startColumn = 0;
			} 
			
			if (endColumn == null) {
				this.endColumn = sheet.getColumns();
			}
			
			checkTotalLine();
			
			this.curRow = this.startRow;
			
			if (this.hasHeader) {
				this.headers = readLineDatas();
			}
			
			context.setAttribute("total.record", this.endRow - this.startRow);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + excelPath, e);
		}
	}

	private void checkTotalLine() {
		int curRow = 0;
		
		for (int r = this.startRow; r < this.endRow; r++) {
			Cell cell = sheet.getCell(this.startColumn, r);
			String cellContents = cell.getContents();
			
			if (StringUtils.isBlank(cellContents)) {
				break;
			}
			
			curRow = r;
		}
		
		this.endRow = curRow + 1;
	}

	public boolean hasNext() {
		return this.curRow < this.endRow;
	}

	private String[] readLineDatas() {
		int startLine = this.curRow;
		
		String[] datas = new String[this.endColumn - this.startColumn];
		
		for (int c = this.startColumn; c < this.endColumn; c++) {
			Cell cell = sheet.getCell(c, startLine);
			String cellContents = cell.getContents();
			datas[c] = cellContents;
		}
		
		this.curRow++;
		
		return datas;
	}
	
	public RecordPage readPage(int pageSize) {
		int curRow = this.curRow;
		int endRow = this.endRow;

		int maxEndRow = this.curRow + pageSize;

		RecordPage page = new SimpleRecordPage(pageSize);

		for (int readRow = curRow; readRow < maxEndRow
				&& readRow < endRow; readRow++) {
			Record record = new SimpleRecord();

			for (int c = this.startColumn; c < this.endColumn; c++) {
				Cell cell = sheet.getCell(c, readRow);
				
				String cellContents = cell.getContents();
				
				if (cell.getType() == CellType.DATE) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					DateCell dc = (DateCell) cell;
					cellContents = sdf.format(dc.getDate());
				} else if (cell.getType() == CellType.NUMBER || cell.getType() == CellType.NUMBER_FORMULA) {
					NumberCell nc = (NumberCell) cell;
					cellContents = String.valueOf(nc.getValue());
				}

				String key = String.valueOf(c);
				if (this.hasHeader) {
					key = this.headers[c];
				}
				
				SimpleDataField cellData = new SimpleDataField(key, DataType.STRING);
				cellData.setData(cellContents);

				record.addDataField(cellData);
			}

			page.writeRecord(record);
		}

		this.curRow = Math.min(maxEndRow, endRow);
		
		return page;
	}

	public void release() {
		if (book != null) {
			book.close();
		}
	}
}
