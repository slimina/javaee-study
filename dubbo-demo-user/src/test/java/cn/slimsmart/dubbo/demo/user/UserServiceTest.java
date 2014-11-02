package cn.slimsmart.dubbo.demo.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;

public class UserServiceTest {

	public static void main(String[] args) throws Exception {
		
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"consumer.xml"});
	        context.start();
	        UserService demoService = (UserService)context.getBean("userService"); 
	        System.out.println("总数："+JSON.json(demoService.findList(null)));
	        User user = new User();
	        user.setId("u_10002");
	        user.setName("李四");
	        demoService.add(user);
	        System.out.println("增加总数："+JSON.json(demoService.findList(null)));
	        demoService.delete("u_10002");
	        System.out.println("删除总数："+JSON.json(demoService.findList(null)));
	        user.setId("u_10001");
	        user.setName("王五");
	        demoService.modify(user);
	        System.out.println("修改总数："+JSON.json(demoService.findList(null)));
	        
	        EchoService echoService = (EchoService) demoService; // 强制转型为EchoService
	        
	        String status = (String) echoService.$echo("OK"); // 回声测试可用性
	         System.out.println(status);
	         
	         //上下文
	         RpcContext.getContext().setAttachment("testContext", JSON.json(user));
	         System.out.println("总数："+JSON.json(demoService.findList(null)));
	}

}
