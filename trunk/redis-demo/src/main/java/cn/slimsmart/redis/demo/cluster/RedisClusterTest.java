package cn.slimsmart.redis.demo.cluster;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClusterTest {

	public static void main(String[] args) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(100);
		config.setMinIdle(100);
		config.setMaxWaitMillis(6 * 1000);
		config.setTestOnBorrow(true);

		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.36.54", 6380));
		jedisClusterNodes.add(new HostAndPort("192.168.36.54", 6381));
		jedisClusterNodes.add(new HostAndPort("192.168.36.54", 6382));
		jedisClusterNodes.add(new HostAndPort("192.168.36.54", 6383));

		jedisClusterNodes.add(new HostAndPort("192.168.36.189", 6380));
		jedisClusterNodes.add(new HostAndPort("192.168.36.189", 6381));
		jedisClusterNodes.add(new HostAndPort("192.168.36.189", 6382));
		jedisClusterNodes.add(new HostAndPort("192.168.36.189", 6383));

		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 2, config);
		try {
			for (int i = 0; i < 1000; i++) {
				long t1 = System.currentTimeMillis();
				jedisCluster.set("" + i, "" + i);
				long t2 = System.currentTimeMillis();
				String value = jedisCluster.get("" + i);
				long t3 = System.currentTimeMillis();
				System.out.println("" + value);
				System.out.println((t2 - t1) + "mills");
				System.out.println((t3 - t2) + "mills");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedisCluster.close();
		}
	}
}