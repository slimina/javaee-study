package cn.slimsmart.zookeeper.demo.curator.count;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.google.common.collect.Lists;

/*
 * 这里我们使用trySetCount去设置计数器。 第一个参数提供当前的VersionedValue,如果期间其它client更新了此计数值，
 *  你的更新可能不成功，
但是这时你的client更新了最新的值，所以失败了你可以尝试再更新一次。
而setCount是强制更新计数器的值。
注意计数器必须start,使用完之后必须调用close关闭它。
在这里再重复一遍前面讲到的， 强烈推荐你监控ConnectionStateListener， 尽管我们的有些例子没有监控它。
 在本例中SharedCountListener扩展了ConnectionStateListener。 这一条针对所有的Curator recipes都适用*/

public class SharedCountTest implements SharedCountListener {
	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";
	private static final int QTY = 5;
	private static final String PATH = "/curator/counter";

	public static void main(String[] args) throws IOException, Exception {
		final Random rand = new Random();
		try {
			CuratorFramework client = CuratorFrameworkFactory.newClient(HOSTS, new ExponentialBackoffRetry(1000, 3));
			client.start();

			SharedCount baseCount = new SharedCount(client, PATH, 0);
			baseCount.addListener(new SharedCountTest());
			baseCount.start();

			List<SharedCount> examples = Lists.newArrayList();
			ExecutorService service = Executors.newFixedThreadPool(QTY);
			for (int i = 0; i < QTY; ++i) {
				final SharedCount count = new SharedCount(client, PATH, 0);
				examples.add(count);
				Callable<Void> task = new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						count.start();
						Thread.sleep(rand.nextInt(10000));
						System.out.println("Increment:" + count.trySetCount(count.getVersionedValue(), count.getCount() + rand.nextInt(10)));
						return null;
					}
				};
				service.submit(task);
			}

			service.shutdown();
			service.awaitTermination(10, TimeUnit.MINUTES);

			for (int i = 0; i < QTY; ++i) {
				examples.get(i).close();
			}
			baseCount.close();
		} finally {

		}
	}

	@Override
	public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
		System.out.println("State changed: " + arg1.toString());
	}

	@Override
	public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
		System.out.println("Counter's value is changed to " + newCount);
	}
}
