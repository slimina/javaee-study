package cn.slimsmart.java.concurrent.blockingdeque;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeTest {
	//生产者
	class Producer implements Runnable {
		private String name;
		private BlockingDeque<Integer> deque;

		public Producer(String name, BlockingDeque<Integer> deque) {
			this.name = name;
			this.deque = deque;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					deque.putFirst(i);
					System.out.println(name + " puts " + i);
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	//消费者
	class Consumer implements Runnable {
		private String name;
		private BlockingDeque<Integer> deque;

		public Consumer(String name, BlockingDeque<Integer> deque) {
			this.name = name;
			this.deque = deque;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					int j = deque.takeLast();
					System.out.println(name + " takes " + j);
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		BlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>(5);
		Runnable producer = new LinkedBlockingDequeTest().new Producer("Producer", deque);
		Runnable consumer = new LinkedBlockingDequeTest().new Consumer("Consumer", deque);
		new Thread(producer).start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(consumer).start();
	}
}
