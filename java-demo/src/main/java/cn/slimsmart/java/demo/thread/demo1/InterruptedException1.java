package cn.slimsmart.java.demo.thread.demo1;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhutw on 2018/3/6.
 */
public class InterruptedException1 implements Runnable{
    @Override // 可以省略
    public void run() {
        // 一直 sleep
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    * public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }
    * */

    public static void main(String[] args) throws Exception {

        Thread interruptedThread = new Thread(new InterruptedException1(), "InterruptedThread");
        interruptedThread.start();
        System.out.println("InterruptedThread interrupted is " + interruptedThread.isInterrupted());
        TimeUnit.SECONDS.sleep(2);
        System.out.println("InterruptedThread interrupted is " + interruptedThread.isInterrupted());
        // 中断被阻塞状态（sleep、wait、join 等状态）的线程，会抛出异常 InterruptedException
        // 在抛出异常 InterruptedException 前，JVM 会先将中断状态重置为默认状态 false
        interruptedThread.interrupt();
        System.out.println("InterruptedThread interrupted is " + interruptedThread.isInterrupted());
        TimeUnit.SECONDS.sleep(10);
        System.out.println("InterruptedThread interrupted is " + interruptedThread.isInterrupted());
    }

}
