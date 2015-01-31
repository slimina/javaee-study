package cn.slimsmart.spring.demo.aop;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public String save(String name) {
		System.out.println("--------save");
		return "save";
	}

	@Override
	public String update(String name) {
		System.out.println("--------update");
		System.out.println(1/0);
		return "update";
	}

	@Override
	public String delete(String name) {
		System.out.println("--------delete");
		return "delete";
	}

}
