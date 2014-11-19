package cn.slimsmart.activemq.demo.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;

public class NotifyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println(new Gson().toJson(message));
		if (message instanceof TextMessage) {
		    try {
				System.out.println("已经收到消息:" + ((TextMessage)message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
