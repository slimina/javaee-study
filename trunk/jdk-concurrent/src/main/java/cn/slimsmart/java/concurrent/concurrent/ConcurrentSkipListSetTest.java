package cn.slimsmart.java.concurrent.concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetTest {

	public static void main(String[] args) {
		  Set<String> set =new ConcurrentSkipListSet<String>();
		  set.add("ccc");
		  set.add("bbb");
		  set.add("aaa");
		  set.add("ccc");
		  System.out.println(set);
	}

}
