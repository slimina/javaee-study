package cn.slimsmart.thrift.demo.spring.http;

import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.slimsmart.thrift.demo.helloworld.HelloWorld;

public class HelloWorldServletTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws TException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/http/applicationContext-servlet-client.xml");
		HelloWorld.Iface client = (HelloWorld.Iface)context.getBean("helloworldService");
		System.out.println(client.sayHello("han meimei"));
	}
}
