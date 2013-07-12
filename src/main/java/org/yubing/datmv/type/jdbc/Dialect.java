package org.yubing.datmv.type.jdbc;

public interface Dialect {

	String buildPageQuery(String baseSql, int startLine, int endLine);

}
