package cn.slimsmart.activemq.demo.spring.msgconverter;

import com.google.gson.Gson;

public class UserMessageListener {

	public void receiveMessage(User user) {
		System.out.println(new Gson().toJson(user));
	}
	
	 public void receiveMessage(String message) {  
	        System.out.println("接收到一个纯文本消息，消息内容是：" + message);  
	 }  
}