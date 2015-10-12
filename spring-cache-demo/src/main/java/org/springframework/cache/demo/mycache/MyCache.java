package org.springframework.cache.demo.mycache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

public class MyCache implements Cache {

	private String name;
	private Map<String, Object> store = new ConcurrentHashMap<String, Object>();;

	public MyCache() {
	}

	public MyCache(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void clear() {
		store.clear();
	}

	public void evict(Object obj) {
	}

	public ValueWrapper get(Object key) {
		ValueWrapper result = null;
		Object thevalue = store.get(key);
		if (thevalue != null) {
			result = new SimpleValueWrapper(thevalue);
		}
		return result;
	}

	public <T> T get(Object key, Class<T> clazz) {
		return clazz.cast(store.get(key));
	}

	public String getName() {
		return name;
	}

	public Object getNativeCache() {
		return store;
	}

	public void put(Object key, Object value) {
		store.put((String) key, value);
	}

	public ValueWrapper putIfAbsent(Object key, Object value) {
		put(key, value);
		return new SimpleValueWrapper(value);
	}
}
