package cn.slimsmart.redis.demo.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {
	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();  
		config.setMaxIdle(0);
		config.setMaxTotal(20);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);
		  
		//创建连接池
		JedisPool pool = new JedisPool(config, "192.168.100.205", 6379);  
		
		//获取客户端
		Jedis redisClient1 = pool.getResource();  
		Jedis redisClient2 = pool.getResource();  
		RedisPubSubListener listener = new RedisPubSubListener();  
		
		 Publisher pub = new Publisher();  
		 pub.publish(redisClient2); //发布一个频道  
		  
		 Subscriber sub = new Subscriber();  
		 sub.psub(redisClient1, listener); // 订阅一个频道  
	}
}
