package cn.slimsmart.zookeeper.demo.curator.leader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

/**
 * 首先我们创建了10个LeaderLatch，启动后它们中的一个会被选举为leader。 因为选举会花费一些时间，start后并不能马上就得到leader。
通过hasLeadership查看自己是否是leader， 如果是的话返回true。
可以通过.getLeader().getId()可以得到当前的leader的ID。
只能通过close释放当前的领导权。
await是一个阻塞方法， 尝试获取leader地位，但是未必能上位。
 *
 */
public class LeaderLatchTest {
	
	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";
	private static final int CLIENT_QTY = 10;
	private static final String PATH = "/curator/leader";

	public static void main(String[] args) throws Exception {
		List<CuratorFramework> clients = Lists.newArrayList();
		List<LeaderLatch> examples = Lists.newArrayList();
		try {
			for (int i = 0; i < CLIENT_QTY; ++i) {
				CuratorFramework client = CuratorFrameworkFactory.newClient(HOSTS, new ExponentialBackoffRetry(1000, 3));
				clients.add(client);
				final LeaderLatch example = new LeaderLatch(client, PATH, "Client #" + i);
				example.addListener(new LeaderLatchListener() {
					@Override
					public void notLeader() {
						System.out.println(example.getId()+" is not leader.");
					}
					
					@Override
					public void isLeader() {
						System.out.println(example.getId()+" is leader.");
					}
				});
				examples.add(example);
				client.start();
				example.start();
			}
			Thread.sleep(20000);
			LeaderLatch currentLeader = null;
			for (int i = 0; i < CLIENT_QTY; ++i) {
				LeaderLatch example = examples.get(i);
				if (example.hasLeadership())
					currentLeader = example;
			}
			System.out.println("current leader is " + currentLeader.getId());
			System.out.println("release the leader " + currentLeader.getId());
			currentLeader.close();
			examples.get(0).await(2, TimeUnit.SECONDS);
			System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
			System.out.println("the new leader is " + examples.get(0).getLeader().getId());

			System.out.println("Press enter/return to quit\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Shutting down...");
			for (LeaderLatch exampleClient : examples) {
				CloseableUtils.closeQuietly(exampleClient);
			}
			for (CuratorFramework client : clients) {
				CloseableUtils.closeQuietly(client);
			}
		}
	}
}
