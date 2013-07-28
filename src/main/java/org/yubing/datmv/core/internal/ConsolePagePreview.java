package org.yubing.datmv.core.internal;

import java.util.Iterator;

import org.yubing.datmv.core.DataField;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.util.StringUtils;
import org.yubing.datmv.util.config.Configuration;

/**
 * 记录页控制台预览实现
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class ConsolePagePreview implements PagePreview {

	public static final int DEFAULT_SPLIT_BYTE_WIDTH = 12;

	private int splitByteWidth = DEFAULT_SPLIT_BYTE_WIDTH;

	public ConsolePagePreview() {
		this.splitByteWidth = Configuration.getIntValue(
				"preview.item.split.bytewidth", DEFAULT_SPLIT_BYTE_WIDTH);
	}

	public void open(MigrateContext context) {
		
	}

	public void title(String title) {
		System.out.println();
		System.out.println(title);
	}

	public void preview(RecordPage recPage) {
		if (recPage != null) {
			previewHeader(recPage);
			recPage.reset();
			while (recPage.hasNext()) {
				Record record = recPage.readRecord();
				preview(record);
			}
		}
	}

	private void previewHeader(RecordPage recPage) {
		if (recPage != null) {
			recPage.reset();
			
			if (recPage.hasNext()) {
				Record record = recPage.readRecord();

				StringBuilder sb = new StringBuilder();
				
				if (record != null) {
					for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
						DataField dataField = it.next();
						sb.append(dataField.getName()).append("\t");
					}
				}
				
				System.out.println(sb);
			}
		}
	}

	public void preview(Record record) {
		StringBuilder sb = new StringBuilder();
		
		if (record != null) {
			for (Iterator<DataField> it = record.iterator(); it.hasNext();) {
				DataField dataField = it.next();
				if (dataField != null) {
					String shortContent = regularData(String.valueOf(dataField.getData()));
					sb.append(shortContent).append("\t");
				} else {
					sb.append("<null>").append("\t");
				}
			}
		}
		
		System.out.println(sb);
	}

	private String regularData(String data) {
		if (data != null) {
			data = StringUtils.splitString(data, splitByteWidth, "...");
			data.replaceAll("\n\r", "");
			data.replaceAll("\n", "");
			data.replaceAll("\r", "");
			data.replaceAll("\t", "");
		}

		return data;
	}

	public void setItemMaxWidth(int width) {
		this.splitByteWidth = width;
	}

	public void release() {
		// TODO Auto-generated method stub

	}
}
