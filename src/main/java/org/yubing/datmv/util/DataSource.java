package org.yubing.datmv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 描述
 * 
 * Author: Wu Cong-Wen Date: 2011-7-9
 */
public class DataSource {
    private Integer TRY_CONNECTION_COUNT = 0;
    private Integer TRY_CONNECTION_SUM = 10;

	private String driverClass;
	private String url;
	private String username;
	private String password;

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
        Connection connection = null;

		Class.forName(driverClass);

        try {
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e) {
            TRY_CONNECTION_COUNT ++;

            if(TRY_CONNECTION_COUNT <= TRY_CONNECTION_SUM){
                return getConnection();
            }
        }

        return connection;
    }

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
