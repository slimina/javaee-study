package cn.slimsmart.zookeeper.demo.curator.EphemeralNode;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode.Mode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.KillSession;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

//一个是临时节点，一个事持久化的节点。 可以看到， client重连后临时节点不存在了。
public class PersistentEphemeralNodeTest {
	
	private static final String PATH = "/curator/ephemeralNode";
	private static final String PATH2 = "/curator/node";
	
	public static void main(String[] args) throws Exception {
		CuratorFramework client = null;
		PersistentEphemeralNode node = null;
		TestingServer server = new TestingServer();
		try {
			client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
			client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
				@Override
				public void stateChanged(CuratorFramework client, ConnectionState newState) {
					System.out.println("client state:" + newState.name());
				}
			});
			client.start();
			
			//http://zookeeper.apache.org/doc/r3.2.2/api/org/apache/zookeeper/CreateMode.html
			node = new PersistentEphemeralNode(client, Mode.EPHEMERAL,PATH, "test".getBytes());
			node.start();
			node.waitForInitialCreate(3, TimeUnit.SECONDS);
			String actualPath = node.getActualPath();
			System.out.println("node " + actualPath + " value: " + new String(client.getData().forPath(actualPath)));
			
			client.create().forPath(PATH2, "persistent node".getBytes());
			System.out.println("node " + PATH2 + " value: " + new String(client.getData().forPath(PATH2)));
			//模拟断掉server
			KillSession.kill(client.getZookeeperClient().getZooKeeper(), server.getConnectString());
			System.out.println("node " + actualPath + " doesn't exist: " + (client.checkExists().forPath(actualPath) == null));
			System.out.println("node " + PATH2 + " value: " + new String(client.getData().forPath(PATH2)));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(node);
			CloseableUtils.closeQuietly(client);
			CloseableUtils.closeQuietly(server);
		}
	}

}
