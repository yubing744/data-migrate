package org.yubing.datmv.type.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.yubing.datmv.core.DataType;
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
	private BufferedReader br;
	private String curLineText;

	public CsvReader(String path) {
		this.csvPath = path;
	}

	public void open() {
		try {
			InputStream is = ResourceUtils.openResource(csvPath);
			br = new BufferedReader(new InputStreamReader(is));
			curLineText = null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error in open " + csvPath, e);
		}
	}

	public boolean hasNext() {
		if (curLineText == null) {
			try {
				curLineText = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (curLineText == null) {
				return false;
			}
		}
		return true;
	}

	private String[] readLineDatas() {
		String[] datas = null;
		if (hasNext()) {
			datas = curLineText.split(",");
			curLineText = null;
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
					
					SimpleDataField cellData = new SimpleDataField(String.valueOf(c), DataType.STRING);
					 
					if (contents.startsWith("\"") && contents.endsWith("\"")) {
						cellData.setData(contents.substring(1, contents
								.length() - 1));
					} else {
						cellData.setData(contents);
					}

					record.addDataField(cellData.getName(), cellData);
				}
			}
			
			page.writeRecord(record);
		}

		return page;
	}

	public void release() {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
