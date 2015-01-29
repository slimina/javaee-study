package cn.slimsmart.java.demo.reflect;

public class ClassTest {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		User user = new User();
		Class c1 = user.getClass();
		Class c2 = User.class;
		Class c3 = Class.forName("cn.slimsmart.java.demo.reflect.User");
		System.out.println(c1==c2);
		System.out.println(c2==c3);
		///通过newInstance方法创建一个user实例对象，使用newInstance方法必须要有一个公共的构造方法
		user = (User)c3.newInstance();
	}

}
