package cn.slimsmart.thrift.demo.pool;

import org.apache.thrift.transport.TSocket;

/**
 * 连接池接口
 *
 */
public interface ConnectionProvider {
	/**
	* 取链接池中的一个链接
	* @return TSocket
	*/
	TSocket getConnection();
	
	/**
	* 返回链接
	* @param socket
	*/
	void returnCon(TSocket socket);
}
