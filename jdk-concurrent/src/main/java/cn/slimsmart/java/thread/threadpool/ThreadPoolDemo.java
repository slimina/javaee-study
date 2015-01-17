package cn.slimsmart.java.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

	public static void main(String[] args) throws InterruptedException {
		// 创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(2);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
		Thread ta = threadPoolDemo.new MyThread();
		Thread tb = threadPoolDemo.new MyThread();
		Thread tc = threadPoolDemo.new MyThread();
		Thread td = threadPoolDemo.new MyThread();
		Thread te = threadPoolDemo.new MyThread();
		long time = System.currentTimeMillis();
		// 将线程放入池中进行执行
		pool.execute(ta);
		pool.execute(tb);
		pool.execute(tc);
		pool.execute(td);
		pool.execute(te);
		// 关闭线程
		// 线程池不再接收新的任务，但是会继续执行完工作队列中现有的任务
		pool.shutdown();
		// 等待关闭线程池，每次等待的超时时间为30秒
		while (!pool.isTerminated()) {
			pool.awaitTermination(30, TimeUnit.SECONDS);
		}
		System.out.println("run times:" +(System.currentTimeMillis()-time)+"ms");
		
		/**
		 * 判断线程执行完毕
		 * 1.thread.join ();
		 * 2.while (true) { 
		 * 	if (exe.isTerminated()) { 
		 * 		System.out.println("结束了！");
		 * 		break; 
		 *  }
		 *  Thread.sleep(200);
		 * }
		 */
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " is running.");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
