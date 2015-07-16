package cn.slimsmart.zeromq.demo.pub_sub;

import org.zeromq.ZMQ;

//http://blog.csdn.net/kobejayandy/article/details/20163345
public class Publisher {

	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1); // 创创建包含一个I/O线程的context
		ZMQ.Socket publisher = context.socket(ZMQ.PUB); // 创建一个publisher类型的socket，他可以向所有订阅的subscriber广播数据

		publisher.bind("tcp://*:5555"); // 将当前publisher绑定到5555端口上，可以接受subscriber的订阅

		while (!Thread.currentThread().isInterrupted()) {
			String message = "topic hello"; // 最开始可以理解为pub的channel，subscribe需要订阅topic这个channel才能接收到消息
			publisher.send(message.getBytes());
		}
		publisher.close();
		context.term();
	}
}
