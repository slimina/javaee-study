package cn.slimsmart.thrift.demo.pool;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:pool/applicationContext-client.xml");
		UserServiceClient userServiceClient = (UserServiceClient) context.getBean(UserServiceClient.class);
		userServiceClient.invoke();
	}
}
