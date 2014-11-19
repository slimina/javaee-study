package cn.slimsmart.activemq.demo.spring.queue;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SuppressWarnings("resource")
public class ProducerMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:producer.xml");
			Queue queue = context.getBean(ActiveMQQueue.class);
	      JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
	      jmsTemplate.convertAndSend(queue,"生产者发送队列消息");
	}
}