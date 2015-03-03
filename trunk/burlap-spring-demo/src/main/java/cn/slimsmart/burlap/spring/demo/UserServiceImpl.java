package cn.slimsmart.burlap.spring.demo;

public class UserServiceImpl implements UserService{

	@Override
	public User getUser(String name) {
		User user = new User();
		user.setAge(30);
		user.setName(name);
		return user;
	}
}
