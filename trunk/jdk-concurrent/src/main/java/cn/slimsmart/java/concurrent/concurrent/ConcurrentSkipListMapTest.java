package cn.slimsmart.java.concurrent.concurrent;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapTest {

	public static void main(String[] args) {
		//key排序
		ConcurrentMap<String,String> map= new ConcurrentSkipListMap<String,String> ();
		map.put("pp", "44");
		map.put("gg", "44");
		map.put("dd", "44");
		map.put("zz", "44");
		map.put("aa", "44");
		System.out.println(map.entrySet());
	}
}
