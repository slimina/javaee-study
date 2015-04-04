package cn.slimsmart.memcache.demo.test.mq;

import net.rubyeye.xmemcached.XMemcachedClient;

//消费
public class Consumer {

	public static void main(String[] args) throws Exception {
	    //XMemcachedClient是线程安全的，可以被多线程使用
	    XMemcachedClient client = new XMemcachedClient("192.168.36.189",22201); 
	    new Thread(new MemcacheQClient(client, "queue_test", new MemcacheQCallback() {
			@Override
			public void receice(Object message) {
				System.out.println("接收到消息："+message);
			}
		})).start();
	}
}
