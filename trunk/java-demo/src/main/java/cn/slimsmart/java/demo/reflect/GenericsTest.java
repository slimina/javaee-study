package cn.slimsmart.java.demo.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GenericsTest {
	public static void main(String[] args) throws Exception{
		//定义一个List集合，指定泛型只能存放String类型
		List<String> list = new ArrayList<String>();
		list.add("hello");
		//下面存储编译器就会报错
		//list.add(123);
		//== 下面我们使用反射添加值看看
		Class c1 = list.getClass();
		Method m = c1.getMethod("add", Object.class);
		//添加 int类型数据
		m.invoke(list, 123);
		System.out.println(list);
	}
}
