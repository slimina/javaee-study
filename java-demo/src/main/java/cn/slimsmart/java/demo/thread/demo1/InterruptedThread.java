package cn.slimsmart.java.demo.thread.demo1;

import java.util.concurrent.TimeUnit;

/**
 * https://my.oschina.net/u/3677020/blog/1629243
 * 不会停止，调用了 interrupt() 方法，中断状态置为 true，但不会影响线程的继续运行
 * 线程中断，不是停止线程，只是一个线程的标志位属性
 *
 */
public class InterruptedThread implements  Runnable{
    @Override // 可以省略
    public void run() {
        // 一直 run
        while (true) {
            System.out.println("running ....");
        }
    }

    public static void main(String[] args) throws Exception {

        Thread interruptedThread = new Thread(new InterruptedThread(), "InterruptedThread");
        interruptedThread.start();

        TimeUnit.SECONDS.sleep(2);

        interruptedThread.interrupt();
        System.out.println("InterruptedThread interrupted is " + interruptedThread.isInterrupted());

        TimeUnit.SECONDS.sleep(2);
    }
}
