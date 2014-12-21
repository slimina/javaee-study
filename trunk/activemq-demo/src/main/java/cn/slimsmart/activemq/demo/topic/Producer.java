package cn.slimsmart.activemq.demo.topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	public static void main(String[] args) throws JMSException {
		// 连接到ActiveMQ服务器
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.110.71:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建主题
		Topic topic = session.createTopic("slimsmart.topic.aaaa");
		MessageProducer producer = session.createProducer(topic);
		// NON_PERSISTENT  非持久化
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		while (true) {
			TextMessage message = session.createTextMessage();
			message.setText("topic 消息。");
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