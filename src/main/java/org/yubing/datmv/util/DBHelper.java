package org.yubing.datmv.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DBHelper {
	
	private DataSource dataSource;
	
	public DBHelper(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection getConnection() throws ClassNotFoundException,
			SQLException {
		return dataSource.getConnection();
	}

	/**
	 * 关闭资源
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	private  void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (st != null) {
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询单行多列
	 * 
	 * @param sql
	 *            查询字符串
	 * @return Map<String,String>
	 */
	public  Map<String, String> querySingleRow(String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			Map<String, String> map = null;
			if (rs.next()) {
				map = new LinkedHashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {// 每循环一次是一列
					// 列名
					String key = rsmd.getColumnName(i);
					// 对应于当前行，当前列的数据
					String value = rs.getString(key);
					map.put(key, value);
				}
			}
			return map;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, st, conn);
		}
		return null;
	}

	/**
	 * 查询多行单列
	 * 
	 * @param sql
	 * @return List<String>
	 */
	public  List<String> querySingleCol(String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<String> list = new LinkedList<String>();
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {// 每循环一次是一行
				// 对应于当前行，当前列的数据
				String value = rs.getString(1);
				list.add(value);
			}
			return list;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, st, conn);
		}
		return null;
	}

	/**
	 * 查询单行单列
	 * 
	 * @param sql
	 * @return String
	 */
	public  String querySingleData(String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {// 每循环一次是一行
				return rs.getString(1);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, st, conn);
		}
		return null;
	}

	/**
	 * 查询多行多列
	 * 
	 * @param sql
	 * @return List<Map<String,String>> 表
	 */
	public  List<Map<String, String>> query(String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new LinkedList<Map<String, String>>();
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			Map<String, String> map = null;
			while (rs.next()) {// 每循环一次是一行
				map = new LinkedHashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {// 每循环一次是一列
					// 列名
					String key = rsmd.getColumnName(i);
					// 对应于当前行，当前列的数据
					
					String value = null;
					try {
						value = rs.getString(key);
					} catch(Exception e) {}
					map.put(key, value);
				}
				list.add(map);
			}
			return list;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, st, conn);
		}
		return null;
	}

	public  String queryAsJson(String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {// 每循环一次是一行
				sb.append("{");
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {// 每循环一次是一列
					// 列名
					String key = rsmd.getColumnName(i);
					// 对应于当前行，当前列的数据
					String value = rs.getString(key);
					sb.append("'" + key + "':'" + value + "'");
					if (i != rsmd.getColumnCount()) {
						sb.append(",");
					}
				}
				sb.append("}");
				sb.append(",");
			}
			if (!"[".equals(sb.toString())) {
				sb = new StringBuffer(sb.substring(0, sb.length() - 1));
			}
			sb.append("]");
			return sb.toString();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, st, conn);
		}
		return null;
	}

	/**
	 * 执行增删改操作
	 * 
	 * @param sql
	 * @return boolean
	 */
	public  boolean modify(String sql) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			int cnt = st.executeUpdate(sql);
			if (cnt > 0) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, st, conn);
		}
		return false;
	}
}