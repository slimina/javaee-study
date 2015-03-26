package cn.slimsmart.memcache.demo.test.cas;

import java.net.InetSocketAddress;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

/**
 * cas测试
 */
public class CASTest {

	public static void main(String[] args) throws Exception {
		MemcachedClient cache = new MemcachedClient(new InetSocketAddress("192.168.100.110", 11211));
		cache.set("number", 1800, "number");
		for (int i = 0; i < 2; i++) {
			new CASThread("thread--" + i, "number" + i).start();
		}
	}
}

class CASThread extends Thread {
	private MemcachedClient cache;
	private String value;

	public CASThread(String name, String value) throws Exception {
		super(name);
		cache = new MemcachedClient(new InetSocketAddress("192.168.100.110", 11211));
		this.value = value;
	}

	@Override
	public void run() {
		int i = 0;
		while (i < 5) {
			i++;
			CASValue<Object> uniqueValue = cache.gets("number");
			CASResponse response = cache.cas("number", uniqueValue.getCas(), value);
			if (response.toString().equals("OK")) {
				System.out.println(Thread.currentThread().getName() + " update success:"+response);
			}else{
				System.out.println(Thread.currentThread().getName() + " update fail:"+response);
			}
		}
	}
}
