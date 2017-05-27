package cn.slimsmart.aop.transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class TransactionManager {
	
	private static ThreadLocal<Map<DataSource,Connection>> connHolder = new ThreadLocal<Map<DataSource,Connection>>();
	
	public synchronized static Connection get(DataSource dataSource){
		return connHolder.get().get(dataSource);
	}

	public synchronized static void set(Connection conn,DataSource dataSource){
		if(connHolder.get()==null){
			connHolder.set(new HashMap<DataSource, Connection>());
		}
		connHolder.get().put(dataSource, conn);
	}
	
	public synchronized static void remove(){
		connHolder.remove();
	}
}
