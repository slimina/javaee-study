package cn.slimsmart.java.demo.thread.demo1;

import junit.framework.TestCase;

import org.junit.Test;

public class JunitThread extends TestCase{

	@Test
	public void testRun() throws InterruptedException{
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println("线程执行开始。。。");
				try {
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("线程执行结束。。。");
			}
		};
		new Thread(task).start();
		Thread.currentThread().sleep(5000);
	}

}
