package cn.slimsmart.rabbitmq.demo.spring.tag;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerMain {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Producer.xml");  
        AmqpTemplate amqpTemplate = context.getBean(RabbitTemplate.class);   
        User user = new User();
        user.setName("niuniu");
        amqpTemplate.convertAndSend(user);
	}
}
