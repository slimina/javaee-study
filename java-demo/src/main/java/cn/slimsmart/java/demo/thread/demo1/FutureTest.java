package cn.slimsmart.java.demo.thread.demo1;

import java.util.concurrent.*;

/**
 * Created by zhutw on 2018/3/9.
 */
public class FutureTest {

    public static void main(String[] args) throws  Exception{
        Runnable task1 = ()->{ //无返回值
            System.out.println("running 1 ..");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (Exception E){}
        };

        Callable task2 = ()->{  //有返回值
            System.out.println("running 2 ..");
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (Exception E){}
            return "success";
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future1= executorService.submit(task1);
        Future future2= executorService.submit(task2);
        /*
        * 用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。
        * 参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，
        * 则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，
        * 此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，若mayInterruptIfRunning设置为true，
        * 则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，
        * 则无论mayInterruptIfRunning为true还是false，肯定返回true。
        * */
        //future1.cancel()

        System.out.println("step 1  ...");
        System.out.println(future1.get()); //get 用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
        System.out.println("step 2 ...");
        System.out.println(future2.get());
        System.out.println("step 3  ...");
        executorService.shutdown();
    }
}
