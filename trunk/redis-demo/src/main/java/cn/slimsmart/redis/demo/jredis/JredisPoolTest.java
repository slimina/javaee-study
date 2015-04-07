package cn.slimsmart.redis.demo.jredis;

import java.util.Date;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jredis.JRedis;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;
import org.jredis.ri.alphazero.support.DefaultCodec;
import org.springframework.data.redis.connection.jredis.JredisPool;

public class JredisPoolTest {

	public static void main(String[] args) throws Exception {
		//链接配置
		 ConnectionSpec connectionSpec = DefaultConnectionSpec.newSpec("192.168.36.189", 6379, 0,null);
		 connectionSpec.setReconnectCnt(100);  
		 //connectionSpec.setConnectionFlag(Connection.Flag.RELIABLE, Boolean.TRUE);
		 //connectionSpec.setHeartbeat(5);  
		 connectionSpec.setMaxConnectWait(3000);
		//连接池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(10);
		poolConfig.setMinIdle(2);
		poolConfig.setMaxIdle(10);
		poolConfig.setMaxWaitMillis(3000);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setTimeBetweenEvictionRunsMillis(3000);
		
		JredisPool jredisPool = new JredisPool(connectionSpec,poolConfig);
		//获取链接
		JRedis jredis = jredisPool.getResource();
		jredis.set("key", "abc");
		if(jredis.exists("key")){  
		   System.out.println(new String(jredis.get("key")));
		}  
		//保存单个对象
		User user = new User();
		user.setBirthDate(new Date());
		user.setName("jack");
		jredis.set("user", user);
		System.out.println(DefaultCodec.decode(jredis.get("user")));
		//回收
		jredisPool.returnResource(jredis);
	}
}
