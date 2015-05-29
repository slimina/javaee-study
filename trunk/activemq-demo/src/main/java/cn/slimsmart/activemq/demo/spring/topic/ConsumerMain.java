package cn.slimsmart.activemq.demo.spring.topic;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
	      new ClassPathXmlApplicationContext("classpath:topic/consumer.xml");
	}
}
