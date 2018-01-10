package cn.slimsmart.java8.stream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import cn.slimsmart.java8.Goods;

public class CollectorsTest {

	public static void main(String[] args) {
		List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");
		Map<String, Long> result = items.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
		System.out.println(result);

		//增加排序实现
		Map<String, Long> finalMap = new LinkedHashMap<>();
		result.entrySet().stream().sorted(Map.Entry.<String,Long>comparingByValue().reversed()).forEachOrdered(e->finalMap.put(e.getKey(), e.getValue()));
		System.out.println(finalMap);
		
		List<Goods> goods = Arrays.asList(
                new Goods("apple", 10, new BigDecimal("9.99")),
                new Goods("banana", 20, new BigDecimal("19.99")),
                new Goods("orang", 10, new BigDecimal("29.99")),
                new Goods("watermelon", 10, new BigDecimal("29.99")),
                new Goods("papaya", 20, new BigDecimal("9.99")),
                new Goods("apple", 10, new BigDecimal("9.99")),
                new Goods("banana", 10, new BigDecimal("19.99")),
                new Goods("apple", 20, new BigDecimal("9.99"))
        );
		// to list
		List<String> list = goods.stream().map(g->g.getName()).collect(Collectors.toList());
		System.out.println(list);
		//to set
		Set<String> set = goods.stream().map(g->g.getName()).collect(Collectors.toCollection(TreeSet::new));
		System.out.println(set);
		//join
		System.out.println(goods.stream().map(g->g.getName()).collect(Collectors.joining(",")));
		System.out.println("-------------------------");
		//map
		System.out.println(goods.stream().collect(Collectors.groupingBy(Goods::getName)));
		System.out.println("-------------------------");
		//Partition
		 Map<Boolean, List<Goods>> passingFailing =goods.stream().collect(Collectors.partitioningBy(g->g.getQty()>10));
		System.out.println(passingFailing);
		
		System.out.println("-------------------------");
		Map<String, Long> counting = goods.stream().collect(Collectors.groupingBy(Goods::getName,Collectors.counting()));
		System.out.println(counting);
		 Map<String, Integer> sum = goods.stream().collect(Collectors.groupingBy(Goods::getName,Collectors.summingInt(Goods::getQty)));
		System.out.println(sum);
		//Price 分组 – Collectors.groupingBy and Collectors.mapping
		
		Map<BigDecimal, List<Goods>> groupByPriceMap = goods.stream().collect(Collectors.groupingBy(Goods::getPrice));
		System.out.println(groupByPriceMap);
		
		// group by price, uses 'mapping' to convert List<Item> to Set<String>
		 Map<BigDecimal, Set<String>> results = goods.stream().collect(Collectors.groupingBy(Goods::getPrice,Collectors.mapping(Goods::getName, Collectors.toSet())));
		 System.out.println(results);

		Stream<Integer> number = Stream.of(1, 2, 3, 4, 5);
		List<Integer> result2 = number.filter(x -> x!= 3).collect(Collectors.toList());
		result2.forEach(x -> System.out.println(x));

        // array 2 stream
        String[] array = {"a", "b", "c", "d", "e"};
        //Arrays.stream
        Stream<String> stream1 = Arrays.stream(array);
        stream1.forEach(x -> System.out.println(x));
        //Stream.of
        Stream<String> stream2 = Stream.of(array);
        stream2.forEach(x -> System.out.println(x));

        int[] intArray = {1, 2, 3, 4, 5};
        // 1. Arrays.stream -> IntStream
        IntStream intStream1 = Arrays.stream(intArray);
        intStream1.forEach(x -> System.out.println(x));
        // 2. Stream.of -> Stream<int[]>
        Stream<int[]> temp = Stream.of(intArray);
        IntStream intStream2 = temp.flatMapToInt(x -> Arrays.stream(x));
        intStream2.forEach(x -> System.out.println(x));
        
        /*
        <R> R collect(Supplier<R> supplier, //容器
                BiConsumer<R, ? super T> accumulator, //计算
                BiConsumer<R, R> combiner);*/ //合并
        
        List<String> lists = goods.parallelStream().map(g->g.getName()).collect(()-> new ArrayList<>(), (c,e)->c.add(e) , (c1,c2)->c1.addAll(c2));
        //stream.map(Object::toString).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        //stream.map(Object::toString).collect(Collectors.toList());
        System.out.println(lists);
    }
}
