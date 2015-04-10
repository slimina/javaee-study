package cn.slimsmart.redis.demo.sentinel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.ShardedJedis;

public class ShardedJedisSentinelTest {

	public static void main(String[] args) {
		
		//连接池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		//分片配置
		List<String> masters = new ArrayList<String>();
		masters.add("shard_a");
		masters.add("shard_b");
		//sentinel服务节点
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("192.168.100.90:6000");
		sentinels.add("192.168.110.71:6000");
		//创建分片连接池
		ShardedJedisSentinelPool pool = new ShardedJedisSentinelPool(poolConfig, masters, sentinels);
		
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set("key_sharded", "abc");
			System.out.println(jedis.get("key_sharded"));
		} finally {
			if (jedis != null){
				pool.returnResource(jedis);
			}
			pool.destroy();
		}
	}
}