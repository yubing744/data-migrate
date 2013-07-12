package org.yubing.datmv.type.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

/**
 * CSV格式数据写入器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class CsvWriter implements PageWriter {

	private String csvPath;
	private BufferedWriter bw;

	public CsvWriter(String path) {
		this.csvPath = path;
	}

	public void open() {
		try {
			bw = new BufferedWriter(new FileWriter(csvPath));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + csvPath, e);
		}
	}

	public void writePage(RecordPage recPage) {
		recPage.reset();

		try {
			while (recPage.hasNext()) {
				Record record = recPage.readRecord();
				writeRecord(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeRecord(Record record) {
		StringBuilder sb = new StringBuilder();
		if (record != null) {
			Set<String> keys = record.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				DataField dataField = record.getDataField(key);
				if ("number".equals(dataField.getType())) {
					sb.append(dataField.getData()).append(",");
				} else {
					sb.append("\"").append(dataField.getData()).append("\"")
							.append(",");
				}
			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
		}

		try {
			bw.append(sb).append("\r\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void release() {
		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
