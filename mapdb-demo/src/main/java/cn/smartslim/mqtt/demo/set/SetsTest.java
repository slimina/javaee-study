package cn.smartslim.mqtt.demo.set;

import java.io.File;
import java.util.NavigableSet;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class SetsTest {
	public static void main(String[] args) {
		//异步写入，关闭事务
		DB db = DBMaker.newFileDB(new File("mapdb.db"))
				.closeOnJvmShutdown().transactionDisable()
				.asyncWriteFlushDelay(100).encryptionEnable("abc123").make();
		NavigableSet<Object> set = (NavigableSet<Object>) db.getTreeSet("NavigableSet");
		set.add("aaa");
		set.add("bbb");
		System.out.println(set);
	}
}
