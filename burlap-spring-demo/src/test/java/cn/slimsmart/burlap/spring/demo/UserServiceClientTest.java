package cn.slimsmart.burlap.spring.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceClientTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-burlap.xml");
		UserService client = context.getBean(UserService.class);
		System.out.println(client.getUser("lucy"));
	}
}
