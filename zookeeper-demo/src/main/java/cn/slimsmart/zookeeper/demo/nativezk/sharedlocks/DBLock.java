package cn.slimsmart.zookeeper.demo.nativezk.sharedlocks;

public interface DBLock {

	enum IsolateLevel {
		READUNCOMMIT, READCOMMIT, NOREPEAT, SERIALIZE
	};

	/**
	 * To get read lock
	 * 
	 * @param group
	 *            . It is group name, which is often organization name.
	 * @param table
	 *            . It is table name.
	 * @throws LockException
	 */
	void readLock(String group, String table) throws LockException;

	/**
	 * To get read lock
	 * 
	 * @param group
	 *            . It is group name, which is often organization name.
	 * @param table
	 *            . It is table name.
	 * @param id
	 *            . It is a primary key, which is often uuid.
	 * @throws LockException
	 */
	void readLock(String group, String table, String id) throws LockException;

	/**
	 * To get write lock
	 * 
	 * @param group
	 *            . It is group name, which is often organization name.
	 * @param table
	 *            . It is table name.
	 * @throws LockException
	 */
	void writeLock(String group, String table) throws LockException;

	/**
	 * To get write lock
	 * 
	 * @param group
	 *            . It is group name, which is often organization name.
	 * @param table
	 *            . It is table name.
	 * @param id
	 *            . It is a primary key, which is often uuid.
	 * @throws LockException
	 */
	void writeLock(String group, String table, String id) throws LockException;

	/**
	 * To release lock
	 * 
	 * @throws LockException
	 */
	void release() throws LockException;
}
