package cn.slimsmart.spring.rpc.annotation.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * UserService测试类
 */
public class UserServiceTest extends ServerRunner{
	@Autowired
	private UserService userService;
	
	@Test
	  public void getUser() {
	    System.out.println(userService.getUser("slim"));
	  }
}
