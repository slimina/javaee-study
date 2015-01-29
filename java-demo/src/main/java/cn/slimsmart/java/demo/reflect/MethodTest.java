package cn.slimsmart.java.demo.reflect;

import java.lang.reflect.Method;

public class MethodTest {
	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setName("jeey");
		user.setAge(20);
		Class c1= user.getClass();
		Method m = c1.getDeclaredMethod("getAge");
		System.out.println("获取age属性值："+m.invoke(user));
		//通过set方法修改属性值
		m = c1.getDeclaredMethod("setAge",int.class);
		m.invoke(user, 99);
		System.out.println("修改后的值："+user.getAge());
	}
}
