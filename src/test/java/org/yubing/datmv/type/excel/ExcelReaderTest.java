package org.yubing.datmv.type.excel;

import org.junit.Before;
import org.junit.Test;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.ConsolePagePreview;
import org.yubing.datmv.type.excel.ExcelReader;

public class ExcelReaderTest {

	public static final int PAGE = 7;
	private PageReader reader;
	private PagePreview pagePreview;

	@Before
	public void setUp() throws Exception {
		reader = new ExcelReader("classpath:/data/excel/test1.xls");
		pagePreview = new ConsolePagePreview();
	}


	/*
	public void testReadPage() {
		try {
			reader.open(null);
			while (reader.hasNext()) {
				RecordPage page = reader.readPage(PAGE);
				System.out.println("page size:" + page.pageSize());
				pagePreview.preview(page);
			}
		} finally {
			reader.release();
		}
	}
	*/
}
