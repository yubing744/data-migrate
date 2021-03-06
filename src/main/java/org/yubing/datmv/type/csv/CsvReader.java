package org.yubing.datmv.type.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

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
 * csv 格式数据读取器
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class CsvReader implements PageReader {

	private String csvPath;
	private Scanner scanner;
	
	private String curLineText;

	private boolean hasHeader;
	private String[] headers;
	
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	public CsvReader(String path) {
		this.csvPath = path;
	}

	public void open(MigrateContext context) {
		try {
			InputStream is = ResourceUtils.openResource(csvPath);
			scanner = new Scanner(is, "UTF-8");
			curLineText = null;
			
			if (this.hasHeader) {
				this.headers = readLineDatas();
			}
		} catch (Exception e) {
			throw new RuntimeException("Error in open " + csvPath, e);
		}
	}

	public boolean hasNext() {
		return scanner.hasNextLine();
	}

	private String[] readLineDatas() {
		String[] datas = null;
		
		if (hasNext()) {
			curLineText = scanner.nextLine();
			datas = curLineText.split(",");
		}
		
		return datas;
	}

	public RecordPage readPage(int pageSize) {
		RecordPage page = new SimpleRecordPage(pageSize);

		for (int readLine = 0; readLine < pageSize; readLine++) {
			Record record = new SimpleRecord();

			String[] datas = readLineDatas();
			if (datas != null) {
				int colSize = datas.length;

				for (int c = 0; c < colSize; c++) {
					String contents = datas[c];
					
					String key = String.valueOf(c);
					if (this.hasHeader) {
						key = this.headers[c];
					}
					
					SimpleDataField cellData = new SimpleDataField(key, DataType.STRING);
					 
					if (contents.startsWith("\"") && contents.endsWith("\"")) {
						cellData.setData(contents.substring(1, contents
								.length() - 1));
					} else {
						cellData.setData(contents);
					}

					record.addDataField(cellData);
				}
				
				page.writeRecord(record);
			} else {
				break;
			}
		}

		return page;
	}

	public void release() {
		if (scanner != null) {
			scanner.close();
		}
	}

}
