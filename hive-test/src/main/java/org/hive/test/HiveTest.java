package org.hive.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveTest {

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive2://192.168.1.93:10000/default";
	private static String username = "";
	private static String password = "";
	private static Connection conn = null;
	private static Statement stmt = null;
	private static String sql = "";
	private static ResultSet res = null;
	static {
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		dropTable("hivetest");
		createTable("hivetest");
		showTables("hivetest");
		describeTables("hivetest");
		insert("hivetest", new String[]{"10000","tom","23"});
		insert("hivetest", new String[]{"10001","zhangshan","80"});
		insert("hivetest", new String[]{"10002","lisi","30"});
		insert("hivetest", new String[]{"10003","lucy","40"});
		selectData("hivetest");
		dropTable("hivetest");
	}

	// 查询数据
	public static void selectData(String tableName) throws SQLException {
		sql = "select * from " + tableName;
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getInt(1) + "\t" + res.getString(2));
		}
	}

	// 添加数据
	public static void insert(String tableName, String[] datas) throws SQLException {
		sql = "insert into table " + tableName + " values ('" + datas[0] + "','" + datas[1] + "'," + Integer.valueOf(datas[2]) + ")";
		stmt.execute(sql);
	}

	// 查询表结构
	public static void describeTables(String tableName) throws SQLException {
		sql = "describe " + tableName;
		res = stmt.executeQuery(sql);
		while (res.next()) {
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}
	}

	// 查看表
	public static void showTables(String tableName) throws SQLException {
		sql = "show tables '" + tableName + "'";
		res = stmt.executeQuery(sql);
		if (res.next()) {
			System.out.println(res.getString(1));
		}
	}

	// 创建表
	public static void createTable(String tableName) throws SQLException {
		sql = "create table " + tableName + " (id string, name string,age int)  row format delimited fields terminated by '\t'";
		stmt.execute(sql);
	}

	// 删除表
	public static String dropTable(String tableName) throws SQLException {
		// 创建的表名
		sql = "drop table " + tableName;
		stmt.execute(sql);
		return tableName;
	}
}
