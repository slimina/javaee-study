package cn.slimsmart.rabbitmq.demo.spring.async;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Receive {

	public static void main(String[] args) {
		 new ClassPathXmlApplicationContext("applicationContext-rabbitmq-async-receive.xml");
	}
}
