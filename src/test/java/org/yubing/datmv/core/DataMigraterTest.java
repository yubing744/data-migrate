package org.yubing.datmv.core;

import org.junit.Before;
import org.junit.Test;
import org.yubing.datmv.config.SimpleMigrateConfig;
import org.yubing.datmv.type.csv.CsvReader;
import org.yubing.datmv.type.jdbc.JdbcWriter;
import org.yubing.datmv.util.DataSource;

public class DataMigraterTest {

	private DataMigrater dm;
	private PageReader pageReader;
	private PageWriter pageWriter;

	@Before
	public void setUp() throws Exception {
		//dm = new DataMigrater();
		// pageReader = new ExcelReader("data/excel/2011年各部门实际审批预算 - 规范数据.xls");
		// pageReader = new
		// ExcelReader("data/excel/2011年咨询项目和预算申报汇总表 - 规范数据.xls");
		// pageReader = new CsvReader("data/csv/我的联系人2.csv");

		/*
		 * DataSource ds = new DataSource();
		 * ds.setDriverClass("com.mysql.jdbc.Driver");
		 * ds.setUrl("jdbc:mysql://localhost:3307/pmdt");
		 * ds.setUsername("root"); ds.setPassword("root");
		 * 
		 * pageReader = new JdbcReader(ds, "t_user");
		 */

		//pageReader = new CsvReader("data/csv/t_user.csv");

		// pageWriter = new
		// ExcelWriter("data/excel/2011年各部门实际审批预算 - 规范数据-输出.xls");
		// pageWriter = new CsvWriter("data/csv/data1.csv");

		// pageWriter = new CsvWriter("data/csv/t_user.csv");

		/*
		DataSource ds = new DataSource();
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3307/pmdt");
		ds.setUsername("root");
		ds.setPassword("root");

		String dialect = "org.yubing.datmv.type.jdbc.dialect.MySqlDialect";

		pageWriter = new JdbcWriter(ds, dialect, "t_user");

		SimpleMigrateConfig config = new SimpleMigrateConfig();
		config.setSourceReader(pageReader);
		config.setTargetWriter(pageWriter);

		config.addConfigItem("f_id", "0");
		config.addConfigItem("f_username", "2");
		config.addConfigItem("f_password", "3");

		dm.setMigrateConfig(config);
		*/
	}

	@Test
	public void testPreview() {
		//dm.preview();
	}

	@Test
	public void testMigrate() {
		//dm.migrate();
	}
}
