package cn.slimsmart.activemq.demo.spring.msgconverter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
	      new ClassPathXmlApplicationContext("classpath:msgconverter/consumer.xml");
	}
}
