package org.yubing.datmv.type.excel;

import java.io.File;
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

/**
 * Excel 数据记录写入器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class ExcelWriter implements PageWriter {

	private OutputStream os;
	private WritableWorkbook wwb;
	private WritableSheet ws;
	private int curLine = 0;
	private String excelPath;

	public ExcelWriter() {
	}

	public ExcelWriter(String filePath) {
		this.excelPath = filePath;
	}

	public void open(MigrateContext context) {
		try {
			File file = new File(this.excelPath);
			if (!file.exists()) {
				throw new RuntimeException("can not find file:" + this.excelPath);
			}

			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			ws = wwb.createSheet("sheet1", 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + this.excelPath, e);
		}
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();
		int curLine = this.curLine;

		int startLine = curLine;
		int endLine = curLine + recPage.pageSize();
		int writeLine = startLine;

		try {

			while (recPage.hasNext() && writeLine < endLine) {
				Record record = recPage.readRecord();
				writeRecord(record, writeLine);
				writeLine++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.curLine = writeLine;
		}
	}

	private void writeRecord(Record record, int writeLine)
			throws RowsExceededException, WriteException {
		if (record != null) {
			Set<String> keys = record.keySet();
			int colNum = 0;
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				DataField dataField = record.getDataField(key);
				writeDataField(dataField, colNum++, writeLine);
			}
		}
	}

	private void writeDataField(DataField dataField, int colNum, int writeLine)
			throws RowsExceededException, WriteException {
		Object data = dataField.getData();
		Label labelCF = new Label(colNum, writeLine, data.toString());
		ws.addCell(labelCF);
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
