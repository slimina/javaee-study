package cn.slimsmart.zookeeper.demo.curator.barrier.DoubleBarrier;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

//分布式Barrier是这样一个类： 它会阻塞所有节点上的等待进程，知道某一个被满足， 然后所有的节点继续进行。
public class DistributedBarrierTest {
	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";
	private static final int QTY = 5;
	private static final String PATH = "/curator/barrier";

	public static void main(String[] args) throws Exception {
		try{
			CuratorFramework client = CuratorFrameworkFactory.newClient(HOSTS, new ExponentialBackoffRetry(1000, 3));
			client.start();
			ExecutorService service = Executors.newFixedThreadPool(QTY);
			for (int i = 0; i < QTY; ++i) {
				final DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, PATH, QTY);
				final int index = i;
				Callable<Void> task = new Callable<Void>() {
					@Override
					public Void call() throws Exception {

						Thread.sleep((long) (3 * Math.random()));
						System.out.println("Client #" + index + " enters");
						barrier.enter();
						System.out.println("Client #" + index + " begins");
						Thread.sleep((long) (3000 * Math.random()));
						barrier.leave();
						System.out.println("Client #" + index + " left");
						return null;
					}
				};
				service.submit(task);
			}

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);
		}finally{
			
		}
	}
}
