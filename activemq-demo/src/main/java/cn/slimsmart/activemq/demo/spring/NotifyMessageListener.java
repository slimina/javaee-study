package cn.slimsmart.activemq.demo.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;

public class NotifyMessageListener implements MessageListener {

	public void onMessage(Message message) {
		System.out.println(new Gson().toJson(message));
		if (message instanceof TextMessage) {
		    try {
				System.out.println("已经收到消息:" + ((TextMessage)message).getText());
				System.out.println("属性:" + ((TextMessage)message).getStringProperty("property"));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
