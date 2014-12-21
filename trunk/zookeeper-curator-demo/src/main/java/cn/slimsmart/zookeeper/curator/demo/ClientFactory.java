package cn.slimsmart.zookeeper.curator.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ClientFactory {

	public static CuratorFramework newClient() {
		//多个以,分割
		String connectionString = "192.168.36.217:2181";
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
	}
}
