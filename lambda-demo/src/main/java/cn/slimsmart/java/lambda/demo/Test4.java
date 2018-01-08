package cn.slimsmart.java.lambda.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 流操作
 */
public class Test4 {

    public static void main(String[] args) {
        List<String> lists = Arrays.asList("tast1","test2","text3","Test4");
        lists.stream().filter(s -> s.startsWith("test")).forEach(System.out::println);
        //等价于
        Predicate<String> predicate = s->s.startsWith("test");
        lists.stream().filter(predicate).forEach(System.out::println);
        //多次过滤
        lists.stream().filter(predicate).filter(s->s.endsWith("2")).forEach(System.out::println);
        System.out.println("-----------------");

        lists.stream().sorted().forEach(System.out::println);
        lists.stream().sorted((p1,p2)->p1.charAt(1)- p2.charAt(1)).forEach(System.out::println);
        System.out.println("-----------------");

        lists.stream().map(String::toUpperCase).forEach(System.out::println);
        Function<String,String> fun = s-> s.substring(4);
        lists.stream().map(fun).map(Integer::valueOf).forEach(System.out::println);

        System.out.println("-----------------");
        //只要有一个匹配
        boolean result = lists.stream().anyMatch(s->s.contains("a"));
        System.out.println(result);
        //全部匹配
        result = lists.stream().allMatch(s->s.contains("s"));
        System.out.println(result);
        //lists.stream().noneMatch()

        System.out.println("-----------------");
        System.out.println(lists.stream().map(fun).map(Integer::valueOf).count());
        List<Integer> integers = lists.stream().map(fun).map(Integer::valueOf).collect(Collectors.toList());
        System.out.println(integers);

        Optional<Integer> sum = lists.stream().distinct().map(fun).map(Integer::valueOf).reduce((n1,n2)->{
            return n1+n2;
        });
        System.out.println(sum.get());
    }
}
