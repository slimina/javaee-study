package cn.slimsmart.memcache.demo.test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.slimsmart.memcache.demo.test.spring.StudentDao;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:applicationContext-cache-xmemcached.xml")  
public class XmemcachedTest  extends AbstractJUnit4SpringContextTests { 
	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void testGet(){
		System.out.println(studentDao.get("db_10001"));
	}
}
