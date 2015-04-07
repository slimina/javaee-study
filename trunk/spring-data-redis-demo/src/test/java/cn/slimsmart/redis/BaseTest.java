package cn.slimsmart.redis;
import java.util.Date;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import cn.slimsmart.redis.spring.data.redis.demo.Order;


public class BaseTest {
	
	protected ApplicationContext applicationContext;
	protected StringRedisTemplate stringRedisTemplate;
	protected RedisTemplate<?, ?> redisTemplate;
	
	protected Order order;
	
	@Before
	public void before() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
		stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
		redisTemplate = (RedisTemplate<?, ?>)applicationContext.getBean("redisTemplate");
		
		order = new Order();
		order.setId("10000");
		order.setOrderNo("AO001");
		order.setPrice(199.6);
		order.setCreateDate(new Date());
	}
}
