package cn.smartslim.mqtt.demo.map;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class MapsTest {
	public static void main(String[] args) {
		//fluet式    无则创建，有则打开，需密码校验
		DB db = DBMaker.newFileDB(new File("mapdb.db")).closeOnJvmShutdown().encryptionEnable("abc123").make();
		/*
		db.getHashMap(name);
		db.getHashSet(name);
		db.getQueue(name);
		db.getTreeMap(name);
		db.getTreeSet(name);
		db.getStack(name);*/	
		ConcurrentNavigableMap<Integer,String> map = db.getTreeMap("ConcurrentMap");
		map.put(1, "test1");
		map.put(2, "test2");
		//提交持久化	
		db.commit();
		
		map.put(3, "test3");
		// map.keySet() is  [1,2,3]
		System.out.println(map);
		//回滚
		db.rollback(); 
		// map.keySet() is  [1,2]
		System.out.println(map);
		db.close();
	}
}
