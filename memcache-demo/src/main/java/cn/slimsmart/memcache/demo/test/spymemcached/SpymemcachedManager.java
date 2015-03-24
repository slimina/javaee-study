package cn.slimsmart.memcache.demo.test.spymemcached;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionObserver;
import net.spy.memcached.DefaultConnectionFactory;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

/**
 * spymemcached采用了单线程异步NIO的方式来管理所有的请求和连接。
 * 源码解析：http://www.tuicool.com/articles/2QNNn2
 */
public class SpymemcachedManager implements Closeable {

	public static int DEFAULT_TIMEOUT = 5;
	public static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
	private MemcachedClient memcachedClient;
	private List<InetSocketAddress> serverList;

	public SpymemcachedManager(List<InetSocketAddress> servers) throws IOException {
		serverList = servers;
		// DefaultConnectionFactory-BinaryConnectionFactory
		// 操作队列长度qLen
		// 读缓冲区bufSize
		ConnectionFactory factory = new DefaultConnectionFactory(DefaultConnectionFactory.DEFAULT_OP_QUEUE_LEN,
				DefaultConnectionFactory.DEFAULT_READ_BUFFER_SIZE);
		/* 建立MemcachedClient 实例，并指定memcached服务的IP地址和端口号 */
		memcachedClient = new MemcachedClient(factory, serverList);
	}

	@Override
	public void close() throws IOException {
		if (memcachedClient != null) {
			memcachedClient.shutdown();
		}
	}

	public void addObserver(ConnectionObserver obs) {
		memcachedClient.addObserver(obs);
	}

	public void removeObserver(ConnectionObserver obs) {
		memcachedClient.removeObserver(obs);
	}

	private boolean getBooleanValue(Future<Boolean> f) {
		try {
			Boolean bool = f.get(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
			return bool.booleanValue();
		} catch (Exception e) {
			f.cancel(false);
			return false;
		}
	}

	public boolean set(String key, Object value, int expire) {
		Future<Boolean> f = memcachedClient.set(key, expire, value);
		return getBooleanValue(f);
	}

	public Object get(String key) {
		return memcachedClient.get(key);
	}

	public Object asyncGet(String key) {
		Object obj = null;
		Future<Object> f = memcachedClient.asyncGet(key);
		try {
			obj = f.get(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
		} catch (Exception e) {
			f.cancel(false);
		}
		return obj;
	}

	public boolean add(String key, Object value, int expire) {
		Future<Boolean> f = memcachedClient.add(key, expire, value);
		return getBooleanValue(f);
	}

	public boolean replace(String key, Object value, int expire) {
		Future<Boolean> f = memcachedClient.replace(key, expire, value);
		return getBooleanValue(f);
	}

	public boolean delete(String key) {
		Future<Boolean> f = memcachedClient.delete(key);
		return getBooleanValue(f);
	}

	public boolean flush() {
		Future<Boolean> f = memcachedClient.flush();
		return getBooleanValue(f);
	}

	public Map<String, Object> getMulti(Collection<String> keys) {
		return memcachedClient.getBulk(keys);
	}

	public Map<String, Object> getMulti(String[] keys) {
		return memcachedClient.getBulk(keys);
	}

	public Map<String, Object> asyncGetMulti(Collection<String> keys) {
		Map<String, Object> map = null;
		Future<Map<String, Object>> f = memcachedClient.asyncGetBulk(keys);
		try {
			map = f.get(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
		} catch (Exception e) {
			f.cancel(false);
		}
		return map;
	}

	public Map<String, Object> asyncGetMulti(String keys[]) {
		Map<String, Object> map = null;
		Future<Map<String, Object>> f = memcachedClient.asyncGetBulk(keys);
		try {
			map = f.get(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
		} catch (Exception e) {
			f.cancel(false);
		}
		return map;
	}

	public long increment(String key, int by, long defaultValue, int expire) {
		return memcachedClient.incr(key, by, defaultValue, expire);
	}

	public long increment(String key, int by) {
		return memcachedClient.incr(key, by);
	}

	public long decrement(String key, int by, long defaultValue, int expire) {
		return memcachedClient.decr(key, by, defaultValue, expire);
	}

	public long decrement(String key, int by) {
		return memcachedClient.decr(key, by);
	}

	public long asyncIncrement(String key, int by) {
		Future<Long> f = memcachedClient.asyncIncr(key, by);
		return getLongValue(f);
	}

	public long asyncDecrement(String key, int by) {
		Future<Long> f = memcachedClient.asyncDecr(key, by);
		return getLongValue(f);
	}

	public void printStats() throws IOException {
		printStats(null);
	}

	public void printStats(OutputStream stream) throws IOException {
		Map<SocketAddress, Map<String, String>> statMap = memcachedClient.getStats();
		if (stream == null) {
			stream = System.out;
		}
		StringBuffer buf = new StringBuffer();
		Set<SocketAddress> addrSet = statMap.keySet();
		Iterator<SocketAddress> iter = addrSet.iterator();
		while (iter.hasNext()) {
			SocketAddress addr = iter.next();
			buf.append(addr.toString() + "/n");
			Map<String, String> stat = statMap.get(addr);
			Set<String> keys = stat.keySet();
			Iterator<String> keyIter = keys.iterator();
			while (keyIter.hasNext()) {
				String key = keyIter.next();
				String value = stat.get(key);
				buf.append("  key=" + key + ";value=" + value + "/n");
			}
			buf.append("/n");
		}
		stream.write(buf.toString().getBytes());
		stream.flush();
	}

	public Transcoder<Object> getTranscoder() {
		return memcachedClient.getTranscoder();
	}

	private long getLongValue(Future<Long> f) {
		try {
			Long l = f.get(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
			return l.longValue();
		} catch (Exception e) {
			f.cancel(false);
		}
		return -1;
	}

}
