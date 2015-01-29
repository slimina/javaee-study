package cn.slimsmart.java.demo.reflect;

import java.lang.reflect.Constructor;

public class ConstructorTest {

	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setName("jeey");
		user.setAge(20);
		Class c1= user.getClass();
		Constructor cons = c1.getDeclaredConstructor(String.class,int.class);
		//通过构造方法创建实例
		User u = (User)cons.newInstance("jack",33);
		System.out.println("创建实例:name="+u.getName()+",age="+u.getAge());
	}

}
