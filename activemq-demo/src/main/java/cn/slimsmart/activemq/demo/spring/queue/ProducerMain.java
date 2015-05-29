package cn.slimsmart.activemq.demo.spring.queue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SuppressWarnings("resource")
public class ProducerMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:queue/producer.xml");
	    JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
	    jmsTemplate.convertAndSend("生产者发送队列消息");
	}
}