package cn.slimsmart.java.demo.thread.demo1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by zhutw on 2018/3/9.
 */
public class FutureTaskTest {

    public static void main(String[] args) throws  Exception{
        FutureTask<String> task = new FutureTask<String>(()->{
            System.out.println("running ...");
            return "hello";
        });
        new Thread(task).start();
        System.out.println("step 1 ...");
        System.out.println(task.get());

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(task);
        System.out.println("step 2 ...");
        System.out.println(task.get());
        System.out.println("step 3 ...");
        executorService.shutdown();
    }

}
