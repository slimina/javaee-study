package cn.slimsmart.spring.httpinvoker.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-httpinvoker-client.xml");
		UserService userService = (UserService)context.getBean("userService");
		User user = new User();
		user.setAge(25);
		user.setName("张三");
		System.out.println(userService.getUser(user));;
	}
}
