package cn.slimsmart.zookeeper.demo.curator.lock.SharedReentrantReadWriteLock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import cn.slimsmart.zookeeper.demo.curator.lock.SharedReentrantLock.FakeLimitedResource;

public class InterProcessReadWriteLockTest {
	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";
	private static final String PATH = "/curator/locks";

	public static void main(String[] args) throws Exception {
		final FakeLimitedResource resource = new FakeLimitedResource();

		CuratorFramework client = CuratorFrameworkFactory.newClient(HOSTS, new ExponentialBackoffRetry(1000, 3));
		try {
			client.start();
			final ExampleClientReadWriteLocks example = new ExampleClientReadWriteLocks(client, PATH, resource, "Client " + 1);
			example.doWork(10, TimeUnit.SECONDS);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
		}
	}
}
