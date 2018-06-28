package cn.slimsmart.redis.demo.jredis;

import java.util.Date;

import org.jredis.JRedis;
import org.jredis.RedisException;
import org.jredis.ri.alphazero.JRedisClient;
import org.jredis.ri.alphazero.support.DefaultCodec;

public class JredisTest {

	public static void main(String[] args) throws Exception {
		JRedis jredis = new JRedisClient("192.168.36.189", 6379);
		//jredis.auth(password);
		try {
			jredis.ping();
			//是否通
			//清空数据库
			jredis.flushdb();
		} catch (RedisException e) {
			e.printStackTrace();
		}
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
		//集合
		jredis.sadd("userList", user);
		user = new User();
		user.setBirthDate(new Date());
		user.setName("lucy");
		jredis.sadd("userList", user);
		System.out.println(DefaultCodec.decode(jredis.smembers("userList")));

		//关闭
		jredis.quit();
	}

}
