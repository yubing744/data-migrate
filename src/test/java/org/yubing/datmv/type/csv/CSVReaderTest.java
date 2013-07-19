package org.yubing.datmv.type.csv;

import org.junit.Before;
import org.junit.Test;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.ConsolePagePreview;
import org.yubing.datmv.type.csv.CsvReader;

/**
 * 描述
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class CSVReaderTest {

	public static final int PAGE = 7;
	private PageReader reader;
	private PagePreview pagePreview;

	@Before
	public void setUp() throws Exception {
		reader = new CsvReader("classpath:/data/csv/t_user.csv");
		pagePreview = new ConsolePagePreview();
	}

	@Test
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
}
