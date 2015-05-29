package cn.slimsmart.activemq.demo.exclusive;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

//队列
public class Producer {

	public static void main(String[] args) throws Exception {
		// 连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://192.168.18.43:61616");
		// 构造从工厂得到连接对象
		Connection connection = connectionFactory.createConnection();
		// 启动
		connection.start();
		// 获取操作连接 一个发送或接收消息的线程
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值slimsmart.queue是一个queue
		Destination destination = session.createQueue("queue.exclusive");
		// 得到消息生成者【发送者】
		MessageProducer producer = session.createProducer(destination);
		// 设置持久化，
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// 构造消息，并发送
		sendMessage(session, producer);
		//提交
		session.commit();
		session.close();
		connection.close();
	}

	public static void sendMessage(Session session, MessageProducer producer) throws Exception {
		for (int i = 1; i <= 5; i++) {
			TextMessage message = session.createTextMessage("发送的消息" + i);
			// 发送消息到目的地方
			System.out.println("发送消息：" + "发送的消息" + i);
			producer.send(message);
		}
	}

}
