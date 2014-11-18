package cn.slimsmart.activemq.demo.topic;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

//持久订阅设置唯一的客户端ID和订阅者ID。
/**
非持久化订阅持续到它们订阅对象的生命周期。这意味着，客户端只能在订阅者活动
时看到相关主题发布的消息。如果订阅者不活动，它会错过相关主题的消息。
 如果花费较大的开销，订阅者可以被定义为durable（持久化的）。持久化的订阅者注
册一个带有JMS保持的唯一标识的持久化订阅（subscription）。带有相同标识的后续订阅
者会再续前一个订阅者的订阅状态。如果持久化订阅没有活动的订阅者，JMS会保持订阅
消息，直到消息被订阅接收或者过期。
 */
public class ConsumerPersistent {

	public static void main(String[] args) throws JMSException {
		// 连接到ActiveMQ服务器
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.110.71:61616");
		Connection connection = factory.createConnection();
		//客户端ID
		connection.setClientID("client_id");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 创建主题
		Topic topic = new ActiveMQTopic("slimsmart.topic.aaaa");
		// 创建持久订阅,指定订阅者ID。
		MessageConsumer consumer = session.createDurableSubscriber(topic,"sub-id");
		consumer.setMessageListener(new MessageListener() {
			// 订阅接收方法
			public void onMessage(Message message) {
				TextMessage tm = (TextMessage) message;
				try {
					System.out.println("Received message: " + tm.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
