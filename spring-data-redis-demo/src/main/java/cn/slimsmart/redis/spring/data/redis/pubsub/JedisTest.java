package cn.slimsmart.redis.spring.data.redis.pubsub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
	static JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	static {
		jedisPoolConfig.setMaxTotal(10);
		jedisPoolConfig.setMaxIdle(2);
	}
	static JedisPool pool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 5000);
	public static void main(String[] args) {
		Jedis jedis = pool.getResource();
		try {
			JedisPubSubListener pubsub = new JedisPubSubListener();
			//订阅
			jedis.subscribe(pubsub, "hq_xq_pubsub");
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();
	}
}
