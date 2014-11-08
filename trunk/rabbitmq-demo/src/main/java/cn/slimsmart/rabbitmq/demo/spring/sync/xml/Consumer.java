package cn.slimsmart.rabbitmq.demo.spring.sync.xml;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");  
        AmqpTemplate amqpTemplate = context.getBean(RabbitTemplate.class);    
        System.out.println("Received: " + amqpTemplate.receiveAndConvert());    
	}

}
