package cn.slimsmart.rabbitmq.demo.headers;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
	private final static String EXCHANGE_NAME = "header-exchange";
	
	@SuppressWarnings("deprecation")
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
		String message = new Date().toLocaleString() + " : log something";
		
		Map<String,Object> headers =  new Hashtable<String, Object>();
		headers.put("aaa", "01234");
		Builder properties = new BasicProperties.Builder();
		properties.headers(headers);
		
		// 指定消息发送到的转发器,绑定键值对headers键值对
		channel.basicPublish(EXCHANGE_NAME, "",properties.build(),message.getBytes());
		
		System.out.println("Sent message :'" + message + "'");
		channel.close();
		connection.close();
	}
}
