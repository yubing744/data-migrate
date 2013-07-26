package org.yubing.datmv.type.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.exception.MigrateException;

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
		
		public static Size max(Size s1, Size s2) {
			return new Size(Math.max(s1.x, s2.x), Math.max(s1.y, s2.y));
		}
	}
	
	protected OutputStream os;
	protected WritableWorkbook wwb;
	protected WritableSheet ws;
	protected int curLine = 0;
	protected String excelPath;

	public ExcelWriter() {}

	public ExcelWriter(String filePath) throws FileNotFoundException {
		this.excelPath = filePath;
		
		File file = new File(this.excelPath);
		if (!file.exists()) {
			throw new RuntimeException("can not find file:" + this.excelPath);
		}

		os = new FileOutputStream(file);
	}

	public ExcelWriter(OutputStream outputStream) {
		this.os = outputStream;
	}

	public void open(MigrateContext context) {
		try {
			wwb = Workbook.createWorkbook(os);
			ws = wwb.createSheet("sheet1", 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + this.excelPath, e);
		}
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();
		Integer curLine = this.curLine;

		int startLine = curLine;
		
		Size max = new Size(0, startLine);
		
		try {
			max = writePage(recPage, max);
		} catch (Exception e) {
			throw new MigrateException("error in write page data!", e);
		} finally {
			this.curLine = this.curLine + max.y;
		}
	}

	protected Size writePage(RecordPage recPage, Size init) throws RowsExceededException, WriteException {
		int writeLine = init.y;
		
		while (recPage.hasNext()) {
			Record record = recPage.readRecord();
			Size writeSize = writeRecord(record, writeLine);
			init = Size.max(init, writeSize);
			writeLine = init.y;
		}
		
		return init;
	}

	protected Size writeRecord(Record record, int currentLine)
			throws RowsExceededException, WriteException {
		Size max = new Size(1, 1);
		
		if (record != null) {
			Set<String> keys = record.keySet();
			int colNum = 0;
			
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				DataField dataField = record.getDataField(key);
				Size size = writeDataField(dataField, colNum,  currentLine);
				max = Size.max(max, size);
			}
		}
		
		return max;
	}

	protected Size writeDataField(DataField dataField, int colNum, int writeLine)
			throws RowsExceededException, WriteException {
		Object data = dataField.getData();
		
		Label labelCF = new Label(colNum, writeLine, String.valueOf(data));
		ws.addCell(labelCF);
		
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
