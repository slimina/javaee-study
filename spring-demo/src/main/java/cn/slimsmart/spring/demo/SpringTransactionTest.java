package cn.slimsmart.spring.demo;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.slimsmart.spring.demo.transaction.UserService;
import cn.slimsmart.spring.demo.transaction.UserServiceImpl;

public class SpringTransactionTest {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-transation.xml");
		UserService userService = applicationContext.getBean(UserService.class);
		System.out.println(AopUtils.isAopProxy(userService));
		System.out.println(AopUtils.isJdkDynamicProxy(userService));
		System.out.println(AopUtils.isCglibProxy(userService));
		userService.save("aaa", 39);
	}
}
