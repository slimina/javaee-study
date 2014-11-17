package cn.slimsmart.activemq.demo.topic;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Test {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建主题
		Topic topic = session.createTopic("myTopic.messages");
		MessageProducer producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		while (true) {
			TextMessage message = session.createTextMessage();
			message.setText("TIME：" + (new Date()).toLocaleString());
			// 发布主题消息
			producer.send(message);
			System.out.println("Sent message: " + message.getText());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
