package cn.slimsmart.thrift.demo.spring.servlet;

import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.slimsmart.thrift.demo.helloworld.HelloWorld;

public class HelloWorldServletProxyTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws TException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/servlet/applicationContext-servlet-client.xml");
		ThriftServletClientProxy thriftServletClientProxy = (ThriftServletClientProxy)context.getBean(ThriftServletClientProxy.class);
		HelloWorld.Iface client = (HelloWorld.Iface)thriftServletClientProxy.getClient();
		System.out.println(client.sayHello("han meimei"));
	}
}
