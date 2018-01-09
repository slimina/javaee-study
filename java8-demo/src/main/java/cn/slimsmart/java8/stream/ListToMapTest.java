package cn.slimsmart.java8.stream;

import cn.slimsmart.java8.Goods;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  */
public class ListToMapTest {
    public static void main(String[] args) {
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
       try{
           Map<String, Integer> result = goods.stream().collect(
                   Collectors.toMap(Goods::getName, Goods::getQty));
           System.out.println("Result 1 : " + result);

           Map<String, Integer> result3 = goods.stream().collect(
                   Collectors.toMap(x -> x.getName(), x -> x.getQty()));
           System.out.println("Result 3 : " + result3);
       }catch (Exception e){
           e.printStackTrace();
       }

        // java.lang.IllegalStateException: Duplicate key 10 解决key冲突
        //为了解决上面重复key的问题，通过增加第三个参数解决:

        Map<String, Integer> result1 = goods.stream().collect(
                Collectors.toMap(x -> x.getName(), x -> x.getQty(),
                        (oldValue, newValue) -> oldValue //如果key是重复的，你选择oldKey or newKey?
                )
        );
        System.out.println(result1);

        Map result2 = goods.stream()
                .sorted(Comparator.comparingLong(Goods::getQty).reversed())
                .collect(
                        Collectors.toMap(
                                Goods::getName, Goods::getQty, // key = name, value = websites
                                (oldValue, newValue) -> oldValue,       // if same key, take the old key
                                LinkedHashMap::new                      // returns a LinkedHashMap, keep order
                        ));
        System.out.println(result2);
    }
}
