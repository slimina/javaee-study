package cn.slimsmart.disconf.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {

	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" }).start();
		Thread.sleep(30000);
	}
}
