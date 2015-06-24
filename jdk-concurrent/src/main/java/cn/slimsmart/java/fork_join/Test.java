package cn.slimsmart.java.fork_join;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		
		ForkJoinPool pool = new ForkJoinPool();
		MyTask task = new MyTask();
		pool.execute(task);
		do {
			System.out.printf("Main: Thread Count: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());
		pool.shutdown();
		if (task.isCompletedNormally()) {
			System.out.printf("Main: The process has completed normally.\n");
		}
		System.out.println("Main: End of the program.\n");
	}
}

class MyTask extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void compute() {
		RecursiveAction t1 = new RecursiveAction() {
			@Override
			protected void compute() {
				try {
					System.out.println(Thread.currentThread().getName()+",sleep 3s....");
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		RecursiveAction t2 = new RecursiveAction() {
			@Override
			protected void compute() {
				try {
					System.out.println(Thread.currentThread().getName()+",sleep 4s....");
					Thread.currentThread().sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		invokeAll(t1, t2);
	}
}