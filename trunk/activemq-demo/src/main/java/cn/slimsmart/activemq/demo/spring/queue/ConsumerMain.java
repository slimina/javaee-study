package cn.slimsmart.activemq.demo.spring.queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
	      new ClassPathXmlApplicationContext("classpath:consumer.xml");
	}
}
