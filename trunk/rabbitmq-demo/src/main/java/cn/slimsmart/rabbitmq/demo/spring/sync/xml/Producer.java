package cn.slimsmart.rabbitmq.demo.spring.sync.xml;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Producer {

	public static void main(String[] args) {
		//生产者
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		AmqpTemplate amqpTemplate = context.getBean(RabbitTemplate.class);  
		amqpTemplate.convertAndSend("test spring sync"); 
	}

}
