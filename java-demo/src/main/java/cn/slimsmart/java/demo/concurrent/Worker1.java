package cn.slimsmart.java.demo.concurrent;

import java.util.concurrent.CountDownLatch;

public class Worker1 implements Runnable{

	private CountDownLatch latch;
	public Worker1(CountDownLatch latch){
		this.latch =latch;
	}
	@Override
	public void run() {
		System.out.println("工人1开始工作");
		try {
			System.out.println("工作中...");
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		latch.countDown();
		System.out.println("工人1完成工作");
	}

}
