package cn.slimsmart.thrift.demo.proxy;

import org.apache.thrift.TException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.slimsmart.thrift.demo.helloworld.HelloWorld;
import cn.slimsmart.thrift.demo.spring.proxy.ThriftClientProxy;

public class ClientTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws TException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:proxy/applicationContext-client.xml");
		ThriftClientProxy thriftClientProxy = (ThriftClientProxy) context.getBean(ThriftClientProxy.class);
		HelloWorld.Iface client = (HelloWorld.Iface)thriftClientProxy.getClient(HelloWorld.class);
		System.out.println(client.sayHello("Jack"));
	}
}
