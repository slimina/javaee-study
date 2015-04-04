package cn.slimsmart.memcache.demo.test.mq;

import net.rubyeye.xmemcached.XMemcachedClient;

//发送消息
public class Producer {

	public static void main(String[] args) throws Exception {
		XMemcachedClient client = new XMemcachedClient("192.168.36.189",22201);
	    for( int i = 0 ; i < 10 ; i++){
	    	client.set("queue_test", 0, "hello world - "+i);
	    	System.out.println("send message : "+"hello world - "+i);
	    }
	}
}
