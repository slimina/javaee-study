package cn.slimsmart.lucene.demo.example;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String id;
	private String name;
	private String desc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static List<User> getInitList(){
		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setId("10001");
		user.setName("张三");
		user.setDesc("张三是个农民，勤劳致富，奔小康");
		list.add(user);
		user = new User();
		user.setId("20001");
		user.setName("李四");
		user.setDesc("李四是个企业家，白手起家，致富一方");
		list.add(user);
		user = new User();
		user.setId("11111");
		user.setName("王五");
		user.setDesc("王五好吃懒做，溜须拍马，跟着李四，也过着小康的日子");
		list.add(user);
		return list;
	}
}
