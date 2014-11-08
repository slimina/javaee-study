package cn.slimsmart.rabbitmq.demo.spring.tag;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("Consumer.xml");  
	}
}
