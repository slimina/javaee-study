package cn.slimsmart.rabbitmq.demo.spring.async;

public class HelloWorldHandler {

	public void handleMessage(String text) {
		System.out.println("Received: " + text);
	}

}
