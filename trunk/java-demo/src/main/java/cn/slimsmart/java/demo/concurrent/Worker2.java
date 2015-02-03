package cn.slimsmart.java.demo.concurrent;

import java.util.concurrent.CountDownLatch;

public class Worker2 implements Runnable{

	private CountDownLatch latch;
	public Worker2(CountDownLatch latch){
		this.latch =latch;
	}
	@Override
	public void run() {
		System.out.println("工人2开始工作");
		try {
			System.out.println("工作中...");
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		latch.countDown();
		System.out.println("工人2完成工作");
	}

}
