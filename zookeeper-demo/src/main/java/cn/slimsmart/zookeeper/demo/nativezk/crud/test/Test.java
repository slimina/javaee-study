package cn.slimsmart.zookeeper.demo.nativezk.crud.test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/*
 create： 创建znode节点。

 delete： 删除znode节点，这里需要注意就是如果要删除的znode节点有子节点，需要先删除所有子节点。

 setData： 修改或设置znode节点的值。

 getData： 获取znode节点的值。

 exists： 判断一个znode节点是不是存在，返回一个Stat对象。

 getChildren： 获取一个节点的子节点。
 * 
 */
public class Test {
	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";
	//  使用了倒数计数，只需要计数一次 
	private static  CountDownLatch connectedSignal  =   new  CountDownLatch( 1 ); 
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper(HOSTS, 5000, new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("event: " + event.getType());
				  //  此处设立在Watch中会在状态变化后触发事件 
			     if  (event.getState()  ==  KeeperState.SyncConnected) { 
			        connectedSignal.countDown(); //  倒数-1 
			    } 
			}
		});
		System.out.println(zk.getState());
		connectedSignal.await();//等待连接完成 
		zk.create("/testAPP", "myAppsData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/testAPP/App1", "App1Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/testAPP/App2", "App2Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/testAPP/App3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.setData("/testAPP/App3", "App3Data".getBytes(), -1);

		System.out.println(zk.exists("/testAPP", true));
		System.out.println(new String(zk.getData("/testAPP", true, null)));

		List<String> children = zk.getChildren("/testAPP", true);
		for (String child : children) {
			System.out.println(new String(zk.getData("/testAPP/" + child, true, null)));
			zk.delete("/testAPP/" + child, -1);
		}

		zk.delete("/testAPP", -1);

		zk.close();

	}

}
