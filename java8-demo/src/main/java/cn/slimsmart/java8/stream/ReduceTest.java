package cn.slimsmart.java8.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReduceTest {

	public static void main(String[] args) {
		Supplier<Stream<Integer>> nums = ()-> Stream.of(5,56,8,1,50,4,16,23);
		//Optional<T> reduce(BinaryOperator<T> accumulator);
		System.out.println(nums.get().reduce((sum,y)->{
			System.out.println(sum + ":" + y);
			return sum+y;
		}).get());
		
		//int count = nums.reduce(0, (sum,y) -> sum+y);
		int count = nums.get().parallel().reduce(0, Integer::sum); //T reduce(T identity, BinaryOperator<T> accumulator);
		System.out.println(count);
		
		System.out.println("---------------");
		Map<Integer,Integer> maps = new HashMap<Integer, Integer>();
		maps.put(132, 1);
		maps.put(1, 33);
		maps.put(111, 13);
		maps.put(212, 1332);
		System.out.println(maps.entrySet().parallelStream().mapToInt(Map.Entry::getKey).max().getAsInt());
		/*
		 * <U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);
		 * */
		//分块计算，再累加  combiner在并行计算起作用
		count = maps.entrySet().stream().parallel().reduce(0, (sum,e) -> {
			System.out.println("sum : "+ sum + ",value : "+e.getValue());
			return sum+e.getValue();
		}, (x,y)->{
			System.out.println(x+";"+y);
			return x+y;
		});
		System.out.println(count);
	}
}
