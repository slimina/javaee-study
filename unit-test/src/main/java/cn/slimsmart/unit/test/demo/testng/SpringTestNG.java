package cn.slimsmart.unit.test.demo.testng;

import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.slimsmart.unit.test.demo.junit.AddService;


@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class SpringTestNG extends AbstractTestNGSpringContextTests {  

	@Autowired
	private AddService addService;
	
	@BeforeClass
	public void setUp(){
		System.out.println("初始化");
	}
	
	@Test
	public void testAdd() {
		assertTrue(addService.add(1, 1) == 2);
	}
	
	@AfterClass
	public void destroy() {
		System.out.println("退出，资源释放");
	}
}
