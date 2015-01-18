package cn.slimsmart.zookeeper.demo.nativezk.queue;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

//参考：http://blog.fens.me/zookeeper-queue/
public class QueueZooKeeper {
	
	   public static void main(String[] args) throws Exception {
	            //doOne();
	            doAction(1);
	    }
	
	 public static void doOne() throws Exception {
	        String host1 = "192.168.36.54:2181";
	        ZooKeeper zk = connection(host1);
	        initQueue(zk);
	        joinQueue(zk, 1);
	        joinQueue(zk, 2);
	        joinQueue(zk, 3);
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
	            joinQueue(zk, 1);
	            break;
	        case 2:
	            zk = connection(host2);
	            initQueue(zk);
	            joinQueue(zk, 2);
	            break;
	        case 3:
	            zk = connection(host3);
	            initQueue(zk);
	            joinQueue(zk, 3);
	            break;
	        }
	    }

	    // 创建一个与服务器的连接
	    public static ZooKeeper connection(String host) throws IOException, InterruptedException {
	    	final CountDownLatch connectedSignal  =   new  CountDownLatch( 1 ); 
	        ZooKeeper zk = new ZooKeeper(host, 50000, new Watcher() {
	            // 监控所有被触发的事件
	            public void process(WatchedEvent event) {
	                if (event.getType() == Event.EventType.NodeCreated && event.getPath().equals("/queue/start")) {
	                    System.out.println("Queue has Completed.Finish testing!!!");
	                }else if(event.getState()  ==  KeeperState.SyncConnected){
	                	  connectedSignal.countDown();
	                }
	            }
	        });
	        connectedSignal.await();
	        return zk;
	    }

	    public static void initQueue(ZooKeeper zk) throws KeeperException, InterruptedException {
	        System.out.println("WATCH => /queue/start");
	        zk.exists("/queue/start", true);

	        if (zk.exists("/queue", false) == null) {
	            System.out.println("create /queue task-queue");
	            zk.create("/queue", "task-queue".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	        } else {
	            System.out.println("/queue is exist!");
	        }
	    }

	    public static void joinQueue(ZooKeeper zk, int x) throws KeeperException, InterruptedException {
	        System.out.println("create /queue/x" + x + " x" + x);
	        zk.create("/queue/x" + x, ("x" + x).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	        isCompleted(zk);
	    }

	    public static void isCompleted(ZooKeeper zk) throws KeeperException, InterruptedException {
	        int size = 3;
	        int length = zk.getChildren("/queue", true).size();

	        System.out.println("Queue Complete:" + length + "/" + size);
	        if (length >= size) {
	            System.out.println("create /queue/start start");
	            zk.create("/queue/start", "start".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	        } 
	    }

}
