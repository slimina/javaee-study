package cn.slimsmart.dubbo.demo.user;

import java.util.List;

public interface UserService {
	
	void add(User user);
	void delete(String userId);
	void modify(User user);
	List<User> findList(String name);
	
}
