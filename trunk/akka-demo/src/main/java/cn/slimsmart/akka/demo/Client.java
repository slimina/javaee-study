package cn.slimsmart.akka.demo;

import akka.actor.ActorRef;
import akka.remote.RemoteActorRef;

public class Client implements Runnable {

	int seq;
	String serviceName;

	Client(int seq, String serviceName) {
		this.seq = seq;
		this.serviceName = serviceName;
	}

	@Override
	public void run() {
		//ActorRef actor =   Remote.actorFor(serviceName, "127.0.0.1", 9999);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Client(i, "client-service"));
			thread.start(); // 同时启动5个客户端请求Master
		}

	}
}
