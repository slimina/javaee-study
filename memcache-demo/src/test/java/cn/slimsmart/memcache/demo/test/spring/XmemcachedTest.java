package cn.slimsmart.memcache.demo.test.spring;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
//@ContextConfiguration(locations="classpath:applicationContext-cache-xmemcached.xml") 
//@ContextConfiguration(locations="classpath:applicationContext-cache-spymemcached.xml")  
//@ContextConfiguration(locations="classpath:applicationContext-cache-aws-elasticache.xml") 
@ContextConfiguration(locations="classpath:applicationContext-cache-spring3.1.xml") 
public class XmemcachedTest  extends AbstractJUnit4SpringContextTests { 
	@Autowired
	private StudentDao studentDao;
	@Autowired 
	private StudentCacheable studentCacheable;
	
	@Test
	public void testGet(){
		System.out.println(studentDao.get("db_10001"));
	}
	
	@Test
	public void testDeleteStudent(){
		Student student = new Student();
		student.setId("db_10001");
		studentDao.deleteStudent(student);
	}
	
	@Test
	public void testGetList(){
		System.out.println(studentDao.getList(Arrays.asList("db_10001")));
	}
	
	@Test
	public void testUpdateStudent(){
		Student s = new Student();
		s.setId("db_10001");
		s.setAge(22);
		s.setName("test_cache");
		s.setBirthday(new Date());
		studentDao.updateStudent(s);
	}
	
	
	//--------载入applicationContext-cache-spring3.1.xml
	@Test
	public void testSpringCacheSave(){
		System.out.println(studentCacheable.get("user_10001"));
	}
	@Test
	public void testSpringCachedelete(){
		Student student = new Student();
		student.setId("user_10001");
		studentCacheable.delete(student);
	}
}
