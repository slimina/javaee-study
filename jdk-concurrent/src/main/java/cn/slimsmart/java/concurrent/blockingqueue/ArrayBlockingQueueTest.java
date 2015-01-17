package cn.slimsmart.java.concurrent.blockingqueue;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(3);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 1, TimeUnit.HOURS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 10; i++) {
			final int index = i;
			System.out.println("task: " + (index + 1));
			Runnable run = new Runnable() {
				@Override
				public void run() {
					System.out.println("thread start" + (index + 1));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("thread end" + (index + 1));
				}
			};
			executor.execute(run);
		}
		executor.shutdown();
	}
}
