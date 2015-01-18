package cn.slimsmart.zookeeper.demo.nativezk.queue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

//http://blog.fens.me/zookeeper-queue-fifo/
public class FIFOZooKeeper {
	public static void main(String[] args) throws Exception {
			//doOne();
			doAction(3);
	}

	public static void doOne() throws Exception {
		String host1 = "192.168.36.54:2181";
		ZooKeeper zk = connection(host1);
		initQueue(zk);
		produce(zk, 1);
		produce(zk, 2);
		cosume(zk);
		cosume(zk);
		cosume(zk);
		zk.close();
	}

	public static void doAction(int client) throws Exception {
		String host1 = "192.168.36.54:2181";
		String host2 = "192.168.36.99:2181";
		String host3 = "192.168.36.189:2181";

		ZooKeeper zk = null;
		switch (client) {
		case 1:
			zk = connection(host1);
			initQueue(zk);
			produce(zk, 1);
			break;
		case 2:
			zk = connection(host2);
			initQueue(zk);
			produce(zk, 2);
			break;
		case 3:
			zk = connection(host3);
			initQueue(zk);
			cosume(zk);
			cosume(zk);
			cosume(zk);
			break;
		}
	}

	// 创建一个与服务器的连接
	public static ZooKeeper connection(String host) throws IOException, InterruptedException {
		final CountDownLatch connectedSignal = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper(host, 50000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				if (event.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});

		connectedSignal.await();
		return zk;
	}

	public static void initQueue(ZooKeeper zk) throws KeeperException, InterruptedException {
		if (zk.exists("/queue-fifo", false) == null) {
			System.out.println("create /queue-fifo task-queue-fifo");
			zk.create("/queue-fifo", "task-queue-fifo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			System.out.println("/queue-fifo is exist!");
		}
	}

	public static void produce(ZooKeeper zk, int x) throws KeeperException, InterruptedException {
		System.out.println("create /queue-fifo/x" + x + " x" + x);
		zk.create("/queue-fifo/x" + x, ("x" + x).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}

	public static void cosume(ZooKeeper zk) throws KeeperException, InterruptedException {
		List<String> list = zk.getChildren("/queue-fifo", true);
		if (list.size() > 0) {
			long min = Long.MAX_VALUE;
			for (String num : list) {
				if (min > Long.parseLong(num.substring(1))) {
					min = Long.parseLong(num.substring(1));
				}
			}
			System.out.println("delete /queue/x" + min);
			zk.delete("/queue-fifo/x" + min, 0);
		} else {
			System.out.println("No node to cosume");
		}
	}
}
