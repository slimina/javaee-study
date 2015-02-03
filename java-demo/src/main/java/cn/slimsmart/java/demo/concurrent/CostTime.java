package cn.slimsmart.java.demo.concurrent;

import java.util.concurrent.CountDownLatch;

public class CostTime {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch=new CountDownLatch(2);//两个线程并行
		long time = System.currentTimeMillis();
		new Thread(new Worker1(latch)).start();
		new Thread(new Worker2(latch)).start();
		latch.await();
		System.out.println("总共花费时间："+(System.currentTimeMillis()-time)+"ms");
	}

}
