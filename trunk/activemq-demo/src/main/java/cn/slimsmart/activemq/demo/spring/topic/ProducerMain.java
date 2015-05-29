package cn.slimsmart.activemq.demo.spring.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@SuppressWarnings("resource")
public class ProducerMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:topic/producer.xml");
	    JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
	    jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();  
                // 设置消息属性  
                msg.setStringProperty("property", "属性");  
                // 设置消息内容  
                msg.setText("生产者发送队列消息");  
                return msg;  
			}
		});
	}
}