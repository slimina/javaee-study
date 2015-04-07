package cn.slimsmart.redis.demo.jredis;

import org.jredis.JRedis;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.JRedisPipelineService;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;

public class JRedisPipelineServiceTest {

	public static void main(String[] args) throws Exception {
		 ConnectionSpec connectionSpec = DefaultConnectionSpec.newSpec("192.168.36.189", 6379, 0,null);
		 connectionSpec.setReconnectCnt(100);  
		 //connectionSpec.setConnectionFlag(Connection.Flag.RELIABLE, Boolean.TRUE);
		 //connectionSpec.setHeartbeat(2);  
		 connectionSpec.setMaxConnectWait(3000);
		 //JRedis jredis = new JRedisClient(connectionSpec);
		 JRedis jredis = new JRedisPipelineService(connectionSpec); 
		 jredis.ping();
		 jredis.set("key", "abc");
		 System.out.println(new String(jredis.get("key")));
		 jredis.quit();
	}
}
