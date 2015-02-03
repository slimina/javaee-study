package cn.slimsmart.spring.data.test;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
	
	@RequestMapping("/getUser/{name}")
	public User getUser(@PathVariable String name){
		User user = new User();
		user.setAge(22);
		user.setName(name);
		user.setId(UUID.randomUUID().toString());
		return user;
	}

}
