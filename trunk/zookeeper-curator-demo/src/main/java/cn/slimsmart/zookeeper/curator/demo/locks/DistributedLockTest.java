package cn.slimsmart.zookeeper.curator.demo.locks;


import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.utils.CloseableUtils;

import cn.slimsmart.zookeeper.curator.demo.ClientFactory;

import com.google.common.collect.Lists;

/**
 * 分布式锁实例
 */
public class DistributedLockTest {
	private static CuratorFramework client = ClientFactory.newClient();
	private static final String PATH = "/locks";

	// 进程内部（可重入）读写锁
	private static final InterProcessReadWriteLock lock;
	// 读锁
	private static final InterProcessLock readLock;
	// 写锁
	private static final InterProcessLock writeLock;

	static {
		lock = new InterProcessReadWriteLock(client, PATH);
		readLock = lock.readLock();
		writeLock = lock.writeLock();
	}

	public static void main(String[] args) {
		client.start();
		try {
			List<Thread> jobs = Lists.newArrayList();
			for (int i = 0; i < 10; i++) {
				Thread t = new Thread(new ParallelJob("Parallel任务" + i, readLock));
				jobs.add(t);
			}

			for (int i = 0; i < 10; i++) {
				Thread t = new Thread(new MutexJob("Mutex任务" + i, writeLock));
				jobs.add(t);
			}

			for (Thread t : jobs) {
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
		}
	}
}
