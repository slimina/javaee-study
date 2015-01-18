package cn.slimsmart.zookeeper.demo.curator.lock.SharedLock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

import cn.slimsmart.zookeeper.demo.curator.lock.SharedReentrantLock.FakeLimitedResource;

public class ExampleClientThatLocks {
	private final InterProcessSemaphoreMutex lock;//不可重入锁,不能在同一个线程中重入
	private final FakeLimitedResource resource;
	private final String clientName;

	public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource resource, String clientName) {
		this.resource = resource;
		this.clientName = clientName;
		lock = new InterProcessSemaphoreMutex(client, lockPath);
	}

	public void doWork(long time, TimeUnit unit) throws Exception {
		if (!lock.acquire(time, unit)) {
			throw new IllegalStateException(clientName + " could not acquire the lock");
		}
		System.out.println(clientName + " has the lock");
		if (!lock.acquire(time, unit)) {  //阻塞
			throw new IllegalStateException(clientName + " could not acquire the lock");
		}
		try {
			System.out.println(clientName + " has the lock");
			resource.use(); // access resource exclusively
		} finally {
			System.out.println(clientName + " releasing the lock");
			lock.release(); // always release the lock in a finally block
			lock.release(); // always release the lock in a finally block
		}
	}
}
