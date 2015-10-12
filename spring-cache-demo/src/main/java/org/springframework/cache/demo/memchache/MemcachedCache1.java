package org.springframework.cache.demo.memchache;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class MemcachedCache1 implements Cache {
	
	private MemcachedClient memcachedClient;
	
	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
	@Override
	public String getName() {
		return memcachedClient.getName();
	}
	@Override
	public Object getNativeCache() {
		return memcachedClient;
	}
	@Override
	public ValueWrapper get(Object key) {
		Object object = null;
		try {
			object = memcachedClient.get((String)key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return (object != null ? new SimpleValueWrapper(object) : null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		Object object = null;
		try {
			object = memcachedClient.get((String)key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return (T)object;
	}
	@Override
	public void put(Object key, Object value) {
		try {
			memcachedClient.set((String) key, 86400, value);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		put(key, value);
		return new SimpleValueWrapper(value);
	}
	@Override
	public void evict(Object key) {
		try {
			memcachedClient.delete((String)key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}  
	}
	@Override
	public void clear() {
		try {
			memcachedClient.flushAll();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}
}
