package cn.slimsmart.activemq.demo.spring.msgconverter;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SuppressWarnings("resource")
public class ProducerMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:msgconverter/producer.xml");
	    JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
	    User user = new User();
	    user.setId("1001");
	    user.setName("jack");
	    user.setAge(22);
	    user.setBirthDate(new Date());
	    jmsTemplate.convertAndSend(user);
	}
}