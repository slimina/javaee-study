package cn.slimsmart.dubbo.demo.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserSercieProvider {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "provider.xml" });
		context.start();
		while(true){
			Thread.sleep(3000);
		}
	}

}
