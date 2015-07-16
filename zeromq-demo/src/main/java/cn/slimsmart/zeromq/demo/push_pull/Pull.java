package cn.slimsmart.zeromq.demo.push_pull;

import java.util.concurrent.atomic.AtomicInteger;

import org.zeromq.ZMQ;

public class Pull {

	public static void main(String[] args) {
		final AtomicInteger number = new AtomicInteger(0);
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				private int here = 0;

				public void run() {
					ZMQ.Context context = ZMQ.context(1);
					ZMQ.Socket pull = context.socket(ZMQ.PULL);
					//IPC 进程间通信
					pull.connect("ipc://msg_test");
					while (true) {
						String message = new String(pull.recv());
						System.out.println(message);
						int now = number.incrementAndGet();
						here++;
						if (now % 1000000 == 0) {
							System.out.println(now + "  here is : " + here);
						}
					}
				}

			}).start();
		}
	}

}
