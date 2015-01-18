package cn.slimsmart.zookeeper.demo.nativezk.crud.hellword;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * ZooKeeper操作类
 * 
 * @author slimina
 */
public class ZooKeeperClient implements Watcher {

	// 缓存时间
	private int timeout = 3000;
	private String hosts = null;
	// /ZooKeeper对象
	protected ZooKeeper zooKeeper = null;

	protected CountDownLatch countDownLatch = new CountDownLatch(1);

	public ZooKeeperClient() {

	}

	public ZooKeeperClient(String hosts, int timeout) {
		this.hosts = hosts;
		this.timeout = timeout;
	}

	// 创建ZK连接
	public void createConnection() throws IOException, InterruptedException {
		zooKeeper = new ZooKeeper(hosts, timeout, this);
		countDownLatch.await();
	}

	// 释放zk连接
	public void releaseConnection() throws InterruptedException {
		if (zooKeeper != null) {
			this.zooKeeper.close();
		}
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 *            节点path
	 * @param data
	 *            初始数据内容
	 * @return
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public void createPath(String path, String data) throws KeeperException, InterruptedException {
		this.zooKeeper.create(path, //创建路径
				data.getBytes(), //路径下的数据
				Ids.OPEN_ACL_UNSAFE, //不进行ACL权限控制，CREATOR_ALL_ACL，READ_ACL_UNSAFE
				CreateMode.PERSISTENT);//创建一个EPHEMERAL类型的节点，会话关闭后它会自动被删除，CreateMode.PERSISTENT持久化不删除，永久性的，CreateMode.PERSISTENT_SEQUENTIAL
	}
	
	//读取指定节点数据内容 
	 public String readData( String path ) throws KeeperException, InterruptedException { 
		 return new String(this.zooKeeper.getData( path, false, null ) ); 
	 }
	
	//更新指定节点数据内容
	 public void  writeData( String path, String data ) throws KeeperException, InterruptedException { 
		 //version，-1 忽略版本号
		 this.zooKeeper.setData( path, data.getBytes(), -1); 
	 }
	
	 //删除指定节点 
	 public void deleteNode( String path ) throws InterruptedException, KeeperException { 
		//version，-1 忽略版本号
		 this.zooKeeper.delete( path, -1 );
	 }
	

	//监控所有被触发的事件 
	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
	}

	public void close() throws InterruptedException {
		zooKeeper.close();
	}
}
