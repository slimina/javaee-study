package cn.slimsmart.java.demo.reflect;

import java.lang.reflect.Array;

public class ArrayTest {

	public static void main(String[] args) {
		String[] aa = {"aa","bb"};
		Class c1 = aa.getClass();
		System.out.println("是否是数值："+c1.isArray());
		System.out.println("数组组件类型的 Class:"+c1.getComponentType());
		//初始化数值
		String[] arr = (String[])Array.newInstance(c1.getComponentType(), 2);
		//数组设值
		Array.set(arr, 0, "111");
		Array.set(arr, 1, "222");
		//访问
		System.out.println(arr[0]);
		System.out.println(Array.get(arr, 1));
	}

}
