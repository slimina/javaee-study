package cn.slimsmart.spring.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.slimsmart.spring.demo.aop.UserService;
import cn.slimsmart.spring.demo.event.AEvent;
import cn.slimsmart.spring.demo.event.BEvent;
import cn.slimsmart.spring.demo.event.TestAsyn;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SpringTest{
	
	@Autowired
	UserService userService;
	@Autowired
	TestAsyn testAsyn;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testStart(){
		System.out.println("启动服务");
		userService.delete("abc123");
		System.out.println("====================");
		userService.update("aaa");
	}
	
	@Test
	public void testEvent(){
		System.out.println("publish event start ...");
		System.out.println("publish event A ...");
		applicationContext.publishEvent(new AEvent("testA"));
		System.out.println("publish event B ...");
		applicationContext.publishEvent(new BEvent("testB"));
		System.out.println("publish event end ...");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAsyn(){
		testAsyn.testAsync();
		System.out.println(1111111);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
