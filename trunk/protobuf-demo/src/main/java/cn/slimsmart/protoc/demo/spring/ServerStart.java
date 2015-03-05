package cn.slimsmart.protoc.demo.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import cn.slimsmart.protoc.demo.rpc.rpc.NonBlockReplyService;

public class ServerStart {

	private static String HOST = "localhost";
	private static int PORT = 12345;

	// Implementation of the Service Interface
	@Bean(name = "nonBlockReplyService")
	public NonBlockReplyService nonBlockReplyService() {
		return new NonBlockReplyService();
	}

	// Will start the server
	@Bean(name = "springServer")
	public SpringServer springServer() {
		return new SpringServer(HOST, PORT);
	}

	public static void main(String[] args) throws Exception {
		new AnnotationConfigApplicationContext(ServerStart.class);
		Thread.sleep(100000);
	}
}
