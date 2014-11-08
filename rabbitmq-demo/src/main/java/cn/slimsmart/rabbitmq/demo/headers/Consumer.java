package cn.slimsmart.rabbitmq.demo.headers;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
	private final static String EXCHANGE_NAME = "header-exchange";
	private final static String QUEUE_NAME = "header-queue";
	
	public static void main(String[] args) throws Exception {
		// 创建连接和频道
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.36.102");
		// 指定用户 密码
		factory.setUsername("admin");
		factory.setPassword("admin");
		// 指定端口
		factory.setPort(AMQP.PROTOCOL.PORT);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//声明转发器和类型headers
		channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.HEADERS,false,true,null);
		channel.queueDeclare(QUEUE_NAME,false, false, true,null);
		
		Map<String, Object> headers = new Hashtable<String, Object>();
		headers.put("x-match", "any");//all any
		headers.put("aaa", "01234");
		headers.put("bbb", "56789");
		// 为转发器指定队列，设置binding 绑定header键值对
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"", headers);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定接收者，第二个参数为自动应答，无需手动应答
		channel.basicConsume(QUEUE_NAME, true, consumer);
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());
			System.out.println(message);
		} 
	}
}
