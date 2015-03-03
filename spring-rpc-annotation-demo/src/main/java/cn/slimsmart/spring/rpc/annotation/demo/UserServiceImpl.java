package cn.slimsmart.spring.rpc.annotation.demo;

import org.springframework.remoting.annotation.RemoteService;
import org.springframework.remoting.annotation.ServiceType;

/**
 * 接口实现类，注解实现服务暴露
 */
@RemoteService(serviceInterface=UserService.class,serviceType=ServiceType.HESSIAN)
public class UserServiceImpl implements UserService{
	@Override
	public User getUser(String name) {
		User user = new User();
		user.setAge(30);
		user.setName(name);
		return user;
	}
}
