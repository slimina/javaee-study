package cn.slimsmart.rabbitmq.demo.spring.sync;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BrokerConfigurationApplication {
	public static void main(String[] args) {
		ApplicationContext context =  new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
		AmqpAdmin amqpAdmin = context.getBean(AmqpAdmin.class);
		Queue helloWorldQueue = new Queue("spring-queue-async");
		amqpAdmin.declareQueue(helloWorldQueue);
	}
}
