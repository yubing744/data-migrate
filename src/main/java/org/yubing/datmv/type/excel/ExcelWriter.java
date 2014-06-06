package org.yubing.datmv.type.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.exception.MigrateException;
import org.yubing.datmv.util.ResourceUtils;

/**
 * Excel 数据记录写入器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class ExcelWriter implements PageWriter {

	public static class Size {
		public Size(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int x;
		public int y;
		
		@Override
		public String toString() {
			return "Size [x=" + x + ", y=" + y + "]";
		}

		public static Size max(Size s1, Size s2) {
			return new Size(Math.max(s1.x, s2.x), Math.max(s1.y, s2.y));
		}
	}
	protected InputStream is;
	protected OutputStream os;
	
	protected WritableWorkbook wwb;
	protected WritableSheet ws;
	protected int sheetIndex = 0;
	
	protected int curLine = 0;
	protected String excelPath;

	protected int startRow = 0;
	protected int startColumn = 0;
	
	
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public ExcelWriter() {}

	public ExcelWriter(String filePath, int sheetIndex) {
		this.excelPath = filePath;
		
		File file = new File(this.excelPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("can not find file:" + this.excelPath, e);
		}
		
		this.sheetIndex = sheetIndex;
	}
	
	public ExcelWriter(String filePath) {
		this(filePath, 0);
	}

	public ExcelWriter(OutputStream outputStream, int sheetIndex) {
		this.os = outputStream;
		this.sheetIndex = sheetIndex;
	}

	public ExcelWriter(OutputStream outputStream) {
		this.os = outputStream;
		this.sheetIndex = 0;
	}
	
	public ExcelWriter(InputStream inputStream, OutputStream outputStream, int sheetIndex) {
		this.is = inputStream;
		this.os = outputStream;
		this.sheetIndex = sheetIndex;
	}
	
	public ExcelWriter(InputStream inputStream, OutputStream outputStream) {
		this.is = inputStream;
		this.os = outputStream;
		this.sheetIndex = 0;
	}
	
	public ExcelWriter(String excelFile , OutputStream outputStream, int sheetIndex) {
		this.is = ResourceUtils.openResource(excelFile);
		this.os = outputStream;
		this.sheetIndex = sheetIndex;
	}
	
	public ExcelWriter(String excelFile , OutputStream outputStream) {
		this.is = ResourceUtils.openResource(excelFile);
		this.os = outputStream;
		this.sheetIndex = 0;
	}
	
	public void open(MigrateContext context) {
		try {
			if (this.is != null) {
				Workbook wb = Workbook.getWorkbook(is);
				wwb = Workbook.createWorkbook(os, wb);
				ws = wwb.getSheet(this.sheetIndex);
			} else {
				wwb = Workbook.createWorkbook(os);
				ws = wwb.createSheet("sheet" + this.sheetIndex, this.sheetIndex);
			}
			
			this.curLine = this.startRow;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + this.excelPath, e);
		}
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();
		Integer curLine = this.curLine;

		int startLine = curLine;
		
		Size size = new Size(0, 0);
		
		try {
			size = writePage(recPage, this.startColumn, startLine, false);
		} catch (Exception e) {
			throw new MigrateException("error in write page data!", e);
		} finally {
			this.curLine = this.curLine + size.y;
		}
	}

	protected Size writePage(RecordPage recPage, int colNum, int rowNum, boolean transpose) throws RowsExceededException, WriteException {
		int writeCol = colNum;
		int writeLine = rowNum;
		
		Size size = new Size(0, 0);
		
		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			Size writeSize = writeRecord(record, writeCol, writeLine, transpose);
			size = Size.max(size, new Size(writeCol - colNum + writeSize.x, writeLine - rowNum + writeSize.y));
			
			if (transpose) {
				writeCol = rowNum + size.x;
			} else {
				writeLine = rowNum + size.y;
			}
		}
		
		return size;
	}

	protected Size writeRecord(Record record, int colNum, int rowNum, boolean transpose)
			throws RowsExceededException, WriteException {
		int writeCol = colNum;
		int writeLine = rowNum;
		
		Size size = new Size(0, 0);
		
		if (record != null) {
			for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
				DataField dataField = it.next();
				Size writeSize = writeDataField(dataField, writeCol, writeLine, transpose);
				size = Size.max(size, new Size(writeCol - colNum + writeSize.x, writeLine - rowNum + writeSize.y));
				
				if (transpose) {
					writeLine = rowNum + size.y;
				} else {
					writeCol = colNum + size.x;
				}
			}
		}
		
		return size;
	}

	public Size writeDataField(DataField dataField, int colNum, int rowNum, boolean transpose)
			throws RowsExceededException, WriteException {
		Object data = dataField.getData();
		
		if (data != null) {
			WritableCell cell = ws.getWritableCell(colNum, rowNum);
			
			CellFormat cf = null;
			
			if (cell != null) {
				cf = cell.getCellFormat();
			} 
			
			if (cf == null){
				WritableCellFormat rwcfF = new WritableCellFormat();
				rwcfF.setAlignment(Alignment.RIGHT);
				cf = rwcfF;
			}
			
			Label labelCF = new Label(colNum, rowNum, String.valueOf(data));
			
			/*
			if (DataType.isNumber(dataField.getType())) {
				WritableCellFormat RwcfF = new WritableCellFormat(cf);
				RwcfF.setAlignment(Alignment.RIGHT);
				labelCF.setCellFormat(RwcfF);
			} else {
				labelCF.setCellFormat(cf);
			}
			*/
			
			ws.addCell(labelCF);
		}
		
		return new Size(1, 1);
	}

	public void release() {
		if (wwb != null) {
			try {
				wwb.write();
				wwb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (os != null) {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
