package cn.slimsmart.rabbitmq.demo.spring.tag;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class ReceiveMessageListener  implements  MessageListener{
	
	public void onMessage(Message message) {
		 System.out.println(" data :" + new String(message.getBody()));
	}
}
