package cn.slimsmart.zookeeper.demo.zkclient;

import java.util.List;

import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.github.zkclient.IZkChildListener;
import com.github.zkclient.IZkClient;
import com.github.zkclient.IZkDataListener;
import com.github.zkclient.IZkStateListener;
import com.github.zkclient.ZkClient;

//https://github.com/adyliu/zkclient/wiki/tutorial
public class ZKClientTest {
	
	private static String path ="/zkclient_path";

	public static void main(String[] args) {
		IZkClient zkClient = new ZkClient("192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181");
		//监听子节点变化
		zkClient.subscribeChildChanges(path, new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("subscribeChildChanges:"+parentPath+"==>"+currentChilds);
			}
		});
		//监听数据节点变化
		zkClient.subscribeDataChanges(path, new IZkDataListener() {
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("delete:"+dataPath);
			}
			
			@Override
			public void handleDataChange(String dataPath, byte[] data) throws Exception {
				System.out.println("data change:"+dataPath+"=>"+new String(data));
			}
		});
		//订阅连接状态的变化
		zkClient.subscribeStateChanges(new IZkStateListener() {
			@Override
			public void handleStateChanged(KeeperState stat) throws Exception {
				System.out.println("handleStateChanged state:"+stat.name());
			}
			
			@Override
			public void handleNewSession() throws Exception {
				System.out.println("handleNewSession");
			}
		});
		
		zkClient.createEphemeral(path);
		zkClient.writeData(path,"test_data".getBytes());
		System.out.println(path+" data:"+new String(zkClient.readData(path)));
		zkClient.delete(path);
	}
}
