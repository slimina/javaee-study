package cn.slimsmart.mongodb.demo.spring;

import java.util.Date;
import java.util.UUID;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");  
		UserRepository userRepository = context.getBean(UserRepository.class);
		userRepository.test();
		userRepository.createCollection();
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("jack");
		user.setBirthday(new Date());
		userRepository.insert(user);
		System.out.println(userRepository.findListByName("jack"));;
	}
}
