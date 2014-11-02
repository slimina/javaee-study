package cn.slimsmart.dubbo.demo.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.util.StringUtils;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.RpcContext;

public class UserServiceImpl implements UserService {
	
	private static Queue<User> userList = new ConcurrentLinkedQueue<User>();

	static{
		User user = new User();
		user.setId("u_10001");
		user.setName("张三");
		userList.add(user);
	}
	
	public void add(User user) {
		System.out.println("add user id="+user.getId());
		userList.add(user);
	}

	public void delete(String userId) {
		System.out.println("delete user id="+userId);
		for(User user : userList){
			if(userId.equals(user.getId())){
				userList.remove(user);
				break;
			}
		}
	}

	public void modify(User u) {
		System.out.println("modify user id="+u.getId());
		for(User user : userList){
			if(u.getId().equals(user.getId())){
				user.setName(u.getName());
				break;
			}
		}
	}

	public List<User> findList(String name) {
		System.out.println("findList user name="+name);
		List<User> list = new ArrayList<User>();
		for(User user : userList){
			if(!StringUtils.hasText(name) || user.getName().indexOf(name) >= 0){
				list.add(user);
			}
		}
		
	Object testContext =  RpcContext.getContext().getAttachment("testContext");
	try {
		System.out.println("上下文testContext:"+JSON.json(testContext));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return list;
	}
}
