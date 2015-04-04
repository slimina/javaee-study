package cn.slimsmart.memcache.demo.test.mq;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

//接收消息客户端
public class MemcacheQClient implements Runnable {

	private MemcachedClient memcachedClient;
	private MemcacheQCallback memcacheQCallback;
	private String queueName;

	public MemcacheQClient() {
	}

	public MemcacheQClient(MemcachedClient memcachedClient, String queueName, MemcacheQCallback memcacheQCallback) {
		this.memcachedClient = memcachedClient;
		this.memcacheQCallback = memcacheQCallback;
		this.queueName = queueName;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Object message = memcachedClient.get(queueName);
				//如果队列中已经没有数据了,休息一下再试
				if(message == null){
					Thread.sleep(10);
				}else{
					memcacheQCallback.receice(message);
				}
			} catch (TimeoutException e) {
			} catch (InterruptedException e) {
			} catch (MemcachedException e) {
			}
		}
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	public MemcacheQCallback getMemcacheQCallback() {
		return memcacheQCallback;
	}

	public void setMemcacheQCallback(MemcacheQCallback memcacheQCallback) {
		this.memcacheQCallback = memcacheQCallback;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
