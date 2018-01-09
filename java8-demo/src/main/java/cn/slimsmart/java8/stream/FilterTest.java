package cn.slimsmart.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.slimsmart.java8.Person;

public class FilterTest {

	public static void main(String[] args) {
		//Streams filter() and collect()
		
		 List<String> lines = Arrays.asList("spring", "node",null, "mkyong");
		 lines.stream().filter(x->"node".equals(x)).collect(Collectors.toList()).forEach(System.out::println);
		
		 lines.stream().filter(Objects::nonNull).collect(Collectors.toList()).forEach(System.out::println);
		 
		//用stream.filter() 过滤List .findAny().orElse (null) 返回一个对象的条件。
		 List<Person> pp = Arrays.asList(new Person(11, "bbb"),new Person(222, "aaa"));
		 System.out.println(pp.stream().filter(x->"aaa".equals(x.getName())).findAny().orElse(null));
		 
		 System.out.println(pp.stream().map(x->x.getName()).collect(Collectors.toList()));
	}

}
