package cn.slimsmart.rabbitmq.demo.spring.async;

public class ReceiveMsgHandler {

	public void handleMessage(String text) {
		System.out.println("Received: " + text);
	}
}
