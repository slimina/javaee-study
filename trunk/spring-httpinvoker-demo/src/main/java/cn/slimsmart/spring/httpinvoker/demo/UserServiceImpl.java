package cn.slimsmart.spring.httpinvoker.demo;

public class UserServiceImpl implements UserService{

	public User getUser(User user) {
		System.out.println(user.toString());
		user.setAge(100);
		user.setName("Hello "+user.getName());
		return user;
	}
}
