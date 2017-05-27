package cn.slimsmart.aop.transaction;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MyDataSource implements DataSource {

	private static final String dirverClassName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/test";
	private static final String user = "root";
	private static final String pswd = "123456";
	
	//模拟线程池
	private static List<Connection> pool = (List<Connection>) Collections
			.synchronizedList(new LinkedList<Connection>());

	private static MyDataSource instance = new MyDataSource();

	static {
		try {
			Class.forName(dirverClassName);
		} catch (ClassNotFoundException e) {
			e.fillInStackTrace();
		}
	}

	private MyDataSource() {
	}

	public synchronized  static MyDataSource instance() {
		if (instance == null)
			instance = new MyDataSource();
		return instance;
	}

	public synchronized Connection getConnection() throws SQLException {
		synchronized (pool) {
			if (pool.size() > 0)
				return pool.remove(0);
			else
				return makeConnection();
		}
	}

	public synchronized static void freeConnection(Connection conn) {
		pool.add(conn);
	}

	private Connection makeConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pswd);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	public void setLoginTimeout(int seconds) throws SQLException {

	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		return null;
	}
}
