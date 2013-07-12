package org.yubing.datmv.type.jdbc;

import org.junit.Before;
import org.junit.Test;
import org.yubing.datmv.core.PagePreview;
import org.yubing.datmv.core.PageReader;
import org.yubing.datmv.core.RecordPage;
import org.yubing.datmv.core.internal.ConsolePagePreview;
import org.yubing.datmv.type.jdbc.JdbcReader;
import org.yubing.datmv.util.DataSource;

/**
 * 描述
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class JdbcReaderTest {

	private static final int PAGE = 7;
	private PageReader reader;
	private PagePreview pagePreview;

	@Before
	public void setUp() throws Exception {
		/*
		DataSource ds = new DataSource();
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3307/pmdt");
		ds.setUsername("root");
		ds.setPassword("root");

		String dialect = "org.yubing.datmv.type.jdbc.dialect.MySqlDialect";

		reader = new JdbcReader(ds, dialect, "t_user");
		pagePreview = new ConsolePagePreview();
		*/
	}

	@Test
	public void testReadPage() {
		/*
		try {
			reader.open();
			while (reader.hasNext()) {
				RecordPage page = reader.readPage(PAGE);
				System.out.println("page size:" + page.pageSize());
				pagePreview.preview(page);
			}
		} finally {
			reader.release();
		}
		*/
	}

}
