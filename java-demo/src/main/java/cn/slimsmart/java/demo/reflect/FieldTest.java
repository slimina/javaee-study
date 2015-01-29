package cn.slimsmart.java.demo.reflect;

import java.lang.reflect.Field;

public class FieldTest {
	public static void main(String[] args) throws Exception {
		User user = new User();
		user.setName("jeey");
		user.setAge(20);
		Class c1= user.getClass();
		Field f = c1.getDeclaredField("name");
		//如下操作不能针对private属性，private只能通过get和set方法访问
		System.out.println("字段类型:"+f.getType().getName());
		System.out.println("字段值："+f.get(user));
		f.set(user, "jock");
		System.out.println("修改后的字段属性值:"+user.getName());
	}

}
