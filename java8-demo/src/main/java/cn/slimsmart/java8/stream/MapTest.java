package cn.slimsmart.java8.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.slimsmart.java8.Person;
import cn.slimsmart.java8.Student;

public class MapTest {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("a", "b", "c", "d");
		list = list.stream().map(x -> x.toUpperCase()).collect(Collectors.toList());
		System.out.println(list);

		list = list.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println(list);

		List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> collect1 = nums.stream().map(n -> n * n).collect(Collectors.toList());
		System.out.println(collect1);
		
		//List of objects -> List of String
		List<Person> pp = Arrays.asList(new Person(11, "bbb"),new Person(222, "aaa"));
		list = pp.stream().map(x->x.getName()).collect(Collectors.toList());
		System.out.println(list);
		
		//List of objects -> List of other objects
		List<Student> ss = pp.stream().map(p->new Student(p.getId(), p.getName())).collect(Collectors.toList());
		System.out.println(ss);
		
		Map<Integer,String> map = new HashMap<>();
		map.put(11, "bbb");
		map.put(22, "aaa");
		map.forEach((k,v)->System.out.println(k+":"+v));
		
		pp.stream().sorted((p1,p2)->p1.getName().compareTo(p2.getName())).collect(Collectors.toList()).forEach(System.out::println);
	}
}
