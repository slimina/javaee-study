package cn.slimsmart.redis.demo.sentinel;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinelTest {

	public static void main(String[] args) {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("192.168.100.90:6000");
		sentinels.add("192.168.110.71:6000");
		
		/**
		 * masterName 分片的名称
		 * sentinels Redis Sentinel 服务地址列表
		 */
		JedisSentinelPool poolA = new JedisSentinelPool("shard_a", sentinels);
		JedisSentinelPool poolB = new JedisSentinelPool("shard_b", sentinels);
		//获取Jedis主服务器客户端实例
		Jedis jedisA = poolA.getResource();
		jedisA.set("key", "abc");
		
		Jedis jedisB = poolB.getResource();
		jedisB.set("key", "xyz");
		
		System.out.println("jedisA key:"+jedisA.get("key"));
		System.out.println("jedisB key:"+jedisB.get("key"));
		//输出结果
		//jedisA key:abc
		//jedisB key:xyz
	}
}
