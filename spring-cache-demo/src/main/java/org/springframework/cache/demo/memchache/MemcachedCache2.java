package org.springframework.cache.demo.memchache;

import java.util.concurrent.TimeoutException;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;

public class MemcachedCache2 implements Cache {

	private com.google.code.ssm.Cache cache;

	public com.google.code.ssm.Cache getCache() {
		return cache;
	}

	public void setCache(com.google.code.ssm.Cache cache) {
		this.cache = cache;
	}

	@Override
	public String getName() {
		return this.cache.getName();
	}

	@Override
	public Object getNativeCache() {
		return this.cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		  Object object = null;  
	        try {  
	            object = this.cache.get((String)key, SerializationType.JAVA);  
	        } catch (TimeoutException e) {  
	            e.printStackTrace();  
	        } catch (CacheException e) {  
	            e.printStackTrace();  
	        }  
	        return (object != null ? new SimpleValueWrapper(object) : null);  
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		try {
			return (T)this.cache.get((String)key, SerializationType.JAVA);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (CacheException e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		try {  
            this.cache.set((String)key, 86400, value, SerializationType.JAVA);  
        } catch (TimeoutException e) {  
            e.printStackTrace();  
        } catch (CacheException e) {  
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
            this.cache.delete((String)key);  
        } catch (TimeoutException e) {  
            e.printStackTrace();  
        } catch (CacheException e) {  
            e.printStackTrace();  
        }  
	}

	@Override
	public void clear() {
		try {  
            this.cache.flush();  
        } catch (TimeoutException e) {  
            e.printStackTrace();  
        } catch (CacheException e) {  
            e.printStackTrace();  
        }  
	}
}
