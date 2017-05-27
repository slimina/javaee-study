package cn.slimsmart.aop.transaction;

import java.sql.Connection;

import javax.sql.DataSource;


public class DataSourceUtils {
	
	public static Connection getConnection(DataSource dataSource){
		return TransactionManager.get(dataSource);
	}
}
