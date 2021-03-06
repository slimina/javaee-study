package cn.slimsmart.activemq.demo.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

	public static void main(String[] args) throws Exception {
		// 连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://192.168.18.43:61616");
		// 构造从工厂得到连接对象
		Connection connection = connectionFactory.createConnection();
		// 启动
		connection.start();
		// 获取操作连接 一个发送或接收消息的线程
		//第一个参数为true  标识开启事务，需要手工commit
		final Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值slimsmart.queue是一个服务器的queue，星号*匹配任意字符
		Destination destination = session.createQueue("slimsmart.queue.test");
		// 消费者，消息接收者
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener( new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMsg = (TextMessage)message;
				try {
					System.out.println("收到消息" + textMsg.getText());
					//session.commit();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		Thread.sleep(100000);
		/*
		while (true) {
            //设置接收者接收消息的时间，为了便于测试，这里设定为100s
            TextMessage message = (TextMessage) consumer.receive(100000);
            if (null != message) {
                System.out.println("收到消息" + message.getText());
            } else {
                break;
            }
        }*/
		session.close();
		connection.close();
	}
}
