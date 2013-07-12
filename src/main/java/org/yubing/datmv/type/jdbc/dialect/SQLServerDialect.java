package org.yubing.datmv.type.jdbc.dialect;

import org.yubing.datmv.type.jdbc.Dialect;

public class SQLServerDialect implements Dialect {

	public String buildPageQuery(String baseSql, int startLine, int endLine) {
		StringBuilder sql = new StringBuilder();
		sql.append("select top ").append(endLine - startLine).append(
				" tmp.* from")
				.append("(select row_number() over (order by id) as ROW_NUM, t.* from (").append(
						baseSql).append(") as t) ").append(
						"as tmp where tmp.ROW_NUM > ").append(startLine);
		return sql.toString();
	}

}
