package cn.slimsmart.memcache.demo.test.xmemcached;

import java.io.IOException;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class XMemcachedManager {

	//MemcachedClientBuilder是MemcachedClient核心接口，用来控制Client的构建（build()方法）和关闭（shutdown()方法）。
	//一般通过构造方法配置地址列表，通常还要配置权重，
	private MemcachedClientBuilder builder;
	
	private MemcachedClient memcachedClient;

	public XMemcachedManager() throws IOException {
		// 多个服务器地址以空格分隔
		builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("192.168.100.110:11211 192.168.100.110:11311 192.168.100.110:11411"), new int[] { 1, 1, 1,
				1 });
		// 设置连接池大小，即客户端个数
		/**
		 * 默认的pool size是1。设置这一数值不一定能提高性能，请依据你的项目的测试结果为准。初步的测试表明只有在大并发下才有提升。  
		 * 设置连接池的一个不良后果就是，同一个memcached的连接之间的数据更新并非同步的  
		 * 因此你的应用需要自己保证数据更新的原子性（采用CAS或者数据之间毫无关联）。  
		 */
		builder.setConnectionPoolSize(10);
		// 宕机报警
		builder.setFailureMode(true);
		// 使用二进制文件
		builder.setCommandFactory(new BinaryCommandFactory());
		// 使用一致性哈希算法（Consistent Hash Strategy）  
		builder.setSessionLocator(new KetamaMemcachedSessionLocator());
		 // 使用序列化传输编码  
		builder.setTranscoder(new SerializingTranscoder());  
	    // 进行数据压缩，大于1KB时进行压缩  
	    builder.getTranscoder().setCompressionThreshold(1024);  
		memcachedClient = builder.build();  
	}
	
	/**
	 * @param 存储的key名称
	 * @param expire 过期时间（单位秒），超过这个时间,memcached将这个数据替换出去，0表示永久存储（默认是一个月)
	 * @param data实际存储的数据
	 * @throws Exception 
	 * @throws  
	 * @throws TimeoutException 
	 */
	public void set(String key,int expire,Object data ) throws Exception{
		memcachedClient.set(key, expire, data);
	}
	
	public Object get(String key) throws Exception{
		return memcachedClient.get(key);
	}
	
	public void delete(String key) throws Exception{
		memcachedClient.delete(key);
	}
	
	public void delete(String key,int expire,Object data ) throws Exception{
		memcachedClient.replace(key, expire, data);
	}
	
	/***
	 * Memcached是通过cas协议实现原子更新，所谓原子更新就是compare and set， 
	 * 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值， memcached比对这个cas值与当前存储数据的cas值是否相等， 
	 * 如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用
	 */
	
	public void setCas(String key,int expire,Object data)throws Exception{
		GetsResponse<Integer> result = memcachedClient.gets(key);
		long cas = result.getCas(); 
		memcachedClient.cas(key, expire, data, cas);
	}
	
	/**
	 * 而删除数据则是通过deleteWithNoReply方法，这个方法删除数据并且告诉memcached 
	 * 不用返回应答，因此这个方法不会等待应答直接返回，特别适合于批量处理 
	 */
	public void deleteWithNoReply(String key)throws Exception{
		memcachedClient.deleteWithNoReply(key);
	}
	
	//递增
	public void incr(String key,int incr)throws Exception{
		//参数指定递增的key名称， 第二个参数指定递增的幅度大小， 第三个参数指定当key不存在的情况下的初始值。 
		//第三个参数，默认指定为0。 
		memcachedClient.incr(key, incr, 0);
	}
	//递减
	public void decr(String key,int decr)throws Exception{
		memcachedClient.decr(key, decr, 0);
	}
}
