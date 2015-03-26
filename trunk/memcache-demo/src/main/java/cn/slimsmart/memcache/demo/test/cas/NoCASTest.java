package cn.slimsmart.memcache.demo.test.cas;

import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * 非cas测试
 */
public class NoCASTest {
	
	public static void main(String[] args) throws Exception{
		MemcachedClient cache = new MemcachedClient(new InetSocketAddress("192.168.100.110", 11211)); 
		cache.set("key", 1800, "key");
		new MThread("thread--1", "X").start();
		new MThread("thread--2","Y").start();
	}
}

class MThread extends Thread {
	private MemcachedClient cache;
	private String value;
	
	public MThread(String name,String value) throws Exception{
		super(name);
		cache = new MemcachedClient(new InetSocketAddress("192.168.100.110", 11211)); 
		this.value=value;
	}

	@Override
	public void run() {
		OperationFuture<Boolean> future = cache.set("key", 1800, value);
		System.out.println(Thread.currentThread().getName()+":"+future.getStatus());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+":"+cache.get("key"));
	}
}
