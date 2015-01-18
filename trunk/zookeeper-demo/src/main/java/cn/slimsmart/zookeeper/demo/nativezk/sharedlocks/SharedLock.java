package cn.slimsmart.zookeeper.demo.nativezk.sharedlocks;

public interface SharedLock {
	
	void write()throws LockException;
	void read()throws LockException;
	void close() throws LockException;
}
