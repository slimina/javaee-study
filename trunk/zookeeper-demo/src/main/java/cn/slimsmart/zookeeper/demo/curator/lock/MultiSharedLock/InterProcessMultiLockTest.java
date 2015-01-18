package cn.slimsmart.zookeeper.demo.curator.lock.MultiSharedLock;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import cn.slimsmart.zookeeper.demo.curator.lock.SharedReentrantLock.FakeLimitedResource;

/*
 * 新建一个InterProcessMultiLock， 包含一个重入锁和一个非重入锁。
调用acquire后可以看到线程同时拥有了这两个锁。
调用release看到这两个锁都被释放了。
再重申以便， 强烈推荐使用ConnectionStateListener监控连接的状态。*/
public class InterProcessMultiLockTest {
	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";
	private static final String PATH1 = "/curator/locks1";
	private static final String PATH2 = "/curator/locks2";

	public static void main(String[] args) throws Exception {
		
		FakeLimitedResource resource = new FakeLimitedResource();
		try {
			CuratorFramework client = CuratorFrameworkFactory.newClient(HOSTS, new ExponentialBackoffRetry(1000, 3));
			client.start();

			InterProcessLock lock1 = new InterProcessMutex(client, PATH1);
			InterProcessLock lock2 = new InterProcessSemaphoreMutex(client, PATH2);

			InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));
			if (!lock.acquire(10, TimeUnit.SECONDS)) {
				throw new IllegalStateException("could not acquire the lock");
			}
			System.out.println("has the lock");

			System.out.println("has the lock1: " + lock1.isAcquiredInThisProcess());
			System.out.println("has the lock2: " + lock2.isAcquiredInThisProcess());

			try {
				resource.use(); // access resource exclusively
			} finally {
				System.out.println("releasing the lock");
				lock.release(); // always release the lock in a finally block
			}
			System.out.println("has the lock1: " + lock1.isAcquiredInThisProcess());
			System.out.println("has the lock2: " + lock2.isAcquiredInThisProcess());
		}finally{
			
		}
	}
}
