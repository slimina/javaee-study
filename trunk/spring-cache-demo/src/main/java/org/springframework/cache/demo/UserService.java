package org.springframework.cache.demo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * 业务服务
 * 
 */
public class UserService {

	@Cacheable(value = "userCache")
	// 使用了一个缓存名叫 userCache
	public User getUserByName(String userName) {
		// 方法内部实现不考虑缓存逻辑，直接实现业务
		System.out.println("real query user." + userName);
		return getFromDB(userName);
	}

	@CacheEvict(value = "userCache", key = "#user.getName()")
	// 清空 accountCache 缓存
	public void updateUser(User user) {
		updateDB(user);
	}

	@CacheEvict(value = "userCache", allEntries = true,beforeInvocation=true)
	// 清空 accountCache 缓存
	public void reload() {
	}

	private User getFromDB(String userName) {
		System.out.println("real querying db..." + userName);
		return new User(userName);
	}

	private void updateDB(User user) {
		System.out.println("real update db..." + user.getName());
	}
}
