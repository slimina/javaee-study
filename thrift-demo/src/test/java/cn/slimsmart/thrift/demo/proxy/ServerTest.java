package cn.slimsmart.thrift.demo.proxy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:proxy/applicationContext-server.xml");
		ThriftServerProxy thriftServerProxy = (ThriftServerProxy) context.getBean(ThriftServerProxy.class);
		thriftServerProxy.start();
	}
}
