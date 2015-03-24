package cn.slimsmart.memcache.demo.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.slimsmart.memcache.demo.test.meetup.MemCachedManager;
import cn.slimsmart.memcache.demo.test.spymemcached.SpymemcachedManager;
import cn.slimsmart.memcache.demo.test.xmemcached.XMemcachedManager;

//测试
public class Test {
	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setUserName("lucy");
		user.setPassword("abc123");
		MemCachedManager.add("user", user, new Date(1000 * 60));// 向Memcached中添加一个序列化的对象
		user = (User) (MemCachedManager.get("user"));
		System.err.println("用户名：" + user.getUserName() + "，密码：" + user.getPassword());
		testSpymemcachedManager(user);
		
		testXMemcachedManager(user);
	}
	
	@SuppressWarnings("resource")
	public static void testSpymemcachedManager(User user) throws IOException{
		List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
		servers.add(new InetSocketAddress("192.168.100.110", 11211));
		servers.add(new InetSocketAddress("192.168.100.110", 11311));
		servers.add(new InetSocketAddress("192.168.100.110", 11411));
		SpymemcachedManager memcachedManager = new SpymemcachedManager(servers);
		memcachedManager.add("user", user, 1000*60);
		user = (User)memcachedManager.get("user");
		System.err.println("用户名：" + user.getUserName() + "，密码：" + user.getPassword());
		memcachedManager.printStats();
	}
	
	public static void testXMemcachedManager(User user) throws Exception{
		XMemcachedManager memcachedManager = new XMemcachedManager();
		memcachedManager.set("user", 1000*60, user);
		user = (User)memcachedManager.get("user");
		System.err.println("用户名：" + user.getUserName() + "，密码：" + user.getPassword());
	}
}
