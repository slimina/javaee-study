package cn.slimsmart.redis.demo.jredis;

import java.util.HashMap;

import org.jredis.JRedis;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.JRedisClient;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;
import org.jredis.ri.alphazero.support.DefaultCodec;

public class RedisFailTolerance {
	private static JRedis jredis = null;

	private static ConnectionSpec defaultConnectionSpec = null;

	private static int current = 1;

	private static HashMap<String, ConnectionSpec> serverPools = new HashMap<String, ConnectionSpec>();
	static {
		ConnectionSpec connectionSpec1 = DefaultConnectionSpec.newSpec("192.168.36.189", 6379, 0, null);
		ConnectionSpec connectionSpec2 = DefaultConnectionSpec.newSpec("192.168.36.54", 6379, 0, null);
		serverPools.put("1", connectionSpec1);
		serverPools.put("2", connectionSpec2);
	}

	private String next() {
		if (current > serverPools.size()) {
			current = 1;
		}
		int nextIndex = current;
		current++;
		return nextIndex + "";
	}

	private ConnectionSpec getConnectionSpec() {
		if (defaultConnectionSpec != null) {
			return defaultConnectionSpec;
		}

		jredis = null;
		/**
		 * we are working multiple servers try different servers,util we fetch
		 * the first available server pool
		 */
		HashMap<String, ConnectionSpec> tryServers = new HashMap<String, ConnectionSpec>(serverPools);
		if (serverPools.size() == 1) {
			return (ConnectionSpec) serverPools.get("1");
		}

		while (!tryServers.isEmpty()) {
			ConnectionSpec connectionSpec = tryServers.get(this.next());

			if (isConnect(connectionSpec)) {
				return connectionSpec;
			}

			tryServers.remove(connectionSpec);
			if (tryServers.isEmpty()) {
				break;
			}
		}

		return null;
	}

	/**
	 * try whether the server is available
	 *
	 * @param connectionSpec
	 * @return true or false
	 */
	private boolean isConnect(ConnectionSpec connectionSpec) {
		if (connectionSpec == null) {
			return false;
		}

		JRedis jredis = new JRedisClient(connectionSpec.getAddress().getHostAddress(), connectionSpec.getPort());
		try {
			jredis.ping();
			jredis.quit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void initialize() {
		defaultConnectionSpec = this.getConnectionSpec();
		if (jredis == null) {
			synchronized (this) {
				jredis = new JRedisClient(defaultConnectionSpec);
			}
		}
	}

	public String getS(String key) {
		this.initialize();
		String value = null;
		try {
			value = DefaultCodec.toStr(jredis.get(key));
		} catch (Exception e) {
			e.printStackTrace();
			defaultConnectionSpec = null;
			this.initialize();
		}

		return value;
	}

	public static void main(String args[]) {
		RedisFailTolerance redis = new RedisFailTolerance();
		System.out.println(redis.getS("key"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(redis.getS("key"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(redis.getS("key"));
	}
}
