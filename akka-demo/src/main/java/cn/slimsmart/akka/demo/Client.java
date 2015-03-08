package cn.slimsmart.akka.demo;

import  akka.actor.Actor;
import akka.actor.ActorSelection;


//多个客户端给Master发请求 
public class Client implements Runnable {

	private String message;

	public Client(String message) {
		this.message = message;
	}

	@Override
	public void run() {
		
		//actorRef actor =    Actor..actorFor(serviceName, "127.0.0.1", 9999);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			//Thread thread = new Thread(new Client(i, "client-service"));
			//thread.start(); // 同时启动5个客户端请求Master
		}

	}
}
