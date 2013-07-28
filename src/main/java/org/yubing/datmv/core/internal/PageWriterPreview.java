package org.yubing.datmv.core.internal;

import org.yubing.datmv.core.DataType;
import org.yubing.datmv.core.MigrateContext;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.core.PageWriter;
import org.yubing.datmv.core.Record;
import org.yubing.datmv.core.RecordPage;

/**
 * 描述
 * 
 * @Author: Wu Cong-Wen
 * @Date: 2011-7-19
 */
public class PageWriterPreview implements PagePreview {
	private PageWriter pageWriter;

	public PageWriterPreview(PageWriter pageWriter) {
		this.pageWriter = pageWriter;
	}

	public void open(MigrateContext context) {
		if (this.pageWriter != null) {
			this.pageWriter.open(context);
		}
	}

	public void preview(RecordPage recPage) {
		pageWriter.writePage(recPage);
	}

	public void setItemMaxWidth(int width) {

	}

	public void title(String title) {
		Record record = new SimpleRecord();
		SimpleDataField titleField = new SimpleDataField("Title", DataType.STRING);
		titleField.setData(title);
		record.addDataField(titleField);
		this.preview(record);
	}

	public void preview(Record record) {
		SimpleRecordPage recPage = new SimpleRecordPage();
		recPage.writeRecord(record);
		this.preview(recPage);
	}

	public void release() {
		if (this.pageWriter != null) {
			this.pageWriter.release();
		}
	}

}
