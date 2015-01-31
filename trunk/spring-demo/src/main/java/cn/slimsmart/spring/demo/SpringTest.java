package cn.slimsmart.spring.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.slimsmart.spring.demo.aop.UserService;

@RunWith(SpringJUnit4ClassRunner.class)//让junit工作在spring环境中
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class SpringTest{
	
	@Autowired
	UserService userService;
	
	@Test
	public void testStart(){
		System.out.println("启动服务");
		userService.delete("abc123");
		System.out.println("====================");
		userService.update("aaa");
	}

}
