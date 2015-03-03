package cn.slimsmart.burlap.spring.demo;

import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceClientTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws TException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-client.xml");
		HelloWorld.Iface client = (HelloWorld.Iface)context.getBean(HelloWorld.Iface.class);
		System.out.println(client.sayHello("han meimei"));
	}
}
