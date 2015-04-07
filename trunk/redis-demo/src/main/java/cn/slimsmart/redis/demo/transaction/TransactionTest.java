package cn.slimsmart.redis.demo.transaction;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TransactionTest {

	public static void main(String[] args) throws InterruptedException {
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis("192.168.36.189", 6379);
		Transaction t=jedis.multi();
		t.set("key1", "aaa");
		Thread.sleep(1000);
		t.set("key2", "bbb");
		List<Object> oList=t.exec();
		System.out.println(oList);
		
		jedis.watch("key1");
		t = jedis.multi();
		t.set("key2", "ccc");
		t.set("key3", "ddd");
		t.get("key3");
		oList = t.exec();
		System.out.println(oList);
	}

}
