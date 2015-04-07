package cn.slimsmart.redis;

import org.junit.Test;
import org.springframework.data.redis.core.ValueOperations;

import cn.slimsmart.redis.spring.data.redis.demo.JsonRedisSeriaziler;

public class JsonRedisSeriazilerTest extends BaseTest{
	
	@Test
	public void seriazilerTest(){
		JsonRedisSeriaziler seriaziler = new JsonRedisSeriaziler();
		 ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();  
	     operations.set("order:" + order.getId(), seriaziler.seriazileAsString(order));  
	     String json = operations.get("order:" + order.getId());  
	     System.out.println(json);
	}
}
