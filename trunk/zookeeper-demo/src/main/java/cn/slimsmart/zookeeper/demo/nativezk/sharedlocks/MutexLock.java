package cn.slimsmart.zookeeper.demo.nativezk.sharedlocks;

public interface MutexLock {
	void lock() throws LockException;
	void close() throws LockException;
}
