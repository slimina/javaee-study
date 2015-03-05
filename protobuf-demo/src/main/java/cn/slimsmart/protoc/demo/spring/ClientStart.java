package cn.slimsmart.protoc.demo.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ClientStart {

	private static String HOST = "localhost";
	private static int PORT = 12345;

	// Will start the server
	@Bean(name = "springClient")
	public SpringClient springClient() {
		return new SpringClient(HOST, PORT);
	}

	public static void main(String[] args) throws Exception {
		new AnnotationConfigApplicationContext(ClientStart.class);
		Thread.sleep(100000);
	}
}
