package cn.slimsmart.zookeeper.demo.nativezk.subpub;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

public class RegisterClient implements Watcher {
	private ZooKeeper zk;
	private boolean connected;
	private String hostStr;
	private int timeOut;

	private String name;
	private boolean created;

	private String path;
	private byte[] data;
	private List<ACL> acl;
	private CreateMode createMode;
	
	public RegisterClient(String hostStr, int timeOut) {
		this.hostStr = hostStr;
		this.timeOut = timeOut;
		try {
			zk = new ZooKeeper(hostStr, timeOut, this);
			connected = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("event: " + event);
		if (event.getState().equals(KeeperState.Disconnected)) {
			connected = false;
		}
		if (event.getState().equals(KeeperState.SyncConnected)) {
			if (!created && !connected) {
				asyncRegist(path, data, acl, createMode);
			}
			connected = true;
		}
		if (event.getState().equals(KeeperState.Expired)) {
			try {
				close();
				created = false;
				zk = new ZooKeeper(hostStr, timeOut, this);
				connected = true;
				asyncRegist(path, data, acl, createMode);//

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void asyncRegist(String path, byte[] data, List<ACL> acl, CreateMode createMode) {
		this.path = path;
		this.data = data;
		this.acl = acl;
		this.createMode = createMode;

		StringCallback callback = new RegisterCallBack(this);
		zk.create(path, data, acl, createMode, callback, null);

	}

	public void asyncRegist(String path, byte[] data, CreateMode createMode) {
		asyncRegist(path, data, Ids.OPEN_ACL_UNSAFE, createMode);
	}

	public void asyncRegist(String path, byte[] data) {
		asyncRegist(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}

	public String getName() {
		return name;
	}

	public void close() {
		try {
			zk.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class RegisterCallBack implements StringCallback {
		private RegisterClient client;

		public RegisterCallBack(RegisterClient client) {
			this.client = client;
		}

		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			if (rc == 0) {
				client.created = true;
				client.name = name;
			}
		}
	}

}
