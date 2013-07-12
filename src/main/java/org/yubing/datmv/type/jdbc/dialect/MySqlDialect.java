package org.yubing.datmv.type.jdbc.dialect;

import org.yubing.datmv.type.jdbc.Dialect;

public class MySqlDialect implements Dialect {

	public String buildPageQuery(String baseSql, int startLine, int endLine) {
		StringBuilder sql = new StringBuilder();
		sql.append(baseSql).append(" limit ").append(startLine).append(",")
				.append(endLine);
		return sql.toString();
	}

}
