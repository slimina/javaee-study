package cn.slimsmart.springmvc.mongodb.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.slimsmart.springmvc.mongodb.entity.User;
import cn.slimsmart.springmvc.mongodb.service.UserService;

/**
 * webapi
 */
@Controller
@RequestMapping("/user")
public class UserController {	
	@Autowired
	private UserService userService; 

	@RequestMapping("/get/{id}")
	@ResponseBody
	public User getUser(@PathVariable String id){
		return userService.get(id);
	}
	
	@RequestMapping("/save/{name}")
	@ResponseBody
	public User saveUser(@PathVariable String name){
		User user = new User();
		user.setId("1234567");
		user.setCreateTime(new Date());
		user.setAge(30);
		user.setUsername(name);
		userService.save(user);
		return user;
	}
}
