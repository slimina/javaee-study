package cn.slimsmart.dubbo.demo.user;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;

public class UserServiceTest {

	public static void main(String[] args) throws Exception {

		APITools tools = new APITools("consumer.xml");
        tools.startService();

        UserService demoService = (UserService)tools.getBean("userService");
        System.out.println("总数："+JSON.json(demoService.findList(null)));

	}

}
