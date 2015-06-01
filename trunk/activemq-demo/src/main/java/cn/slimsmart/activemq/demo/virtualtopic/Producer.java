package cn.slimsmart.activemq.demo.virtualtopic;

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
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.18.67:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		// 创建主题
		Topic topic = session.createTopic("VirtualTopic.TEST");
		MessageProducer producer = session.createProducer(topic);
		// NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage message = session.createTextMessage();
		message.setText("topic 消息。");
		message.setStringProperty("property", "消息Property");
		// 发布主题消息
		producer.send(message);
		System.out.println("Sent message: " + message.getText());
		session.close();
		connection.close();
	}
}
