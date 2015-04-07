package cn.slimsmart.redis.demo.jedis;

import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {
	public static void main(String[] args) {
		ResourceBundle bundle = ResourceBundle.getBundle("redis");
		if (bundle == null) {
			throw new IllegalArgumentException("[redis.properties] is not found!");
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxTotal")));
		config.setMinIdle(Integer.valueOf(bundle.getString("redis.pool.minIdle")));
		config.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
		config.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
		config.setTestWhileIdle(Boolean.valueOf(bundle.getString("redis.pool.testWhileIdle")));
		config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
		config.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));
		JedisPool pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));

		// 从池中获取一个Jedis对象
		Jedis jedis = pool.getResource();
		String key = "key";
		// 删数据
		jedis.del(key);
		// 存数据
		jedis.set(key, "abc123");
		// 取数据
		String value = jedis.get(key);
		System.out.println(value);
		// 释放对象池
		pool.returnResource(jedis);
	}

}
