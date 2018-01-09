package cn.slimsmart.java8.stream;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对一个 Map 进行按照keys或者values排序
 * 将 Map 转换为 Stream
 对其进行排序
 Collect and return a new LinkedHashMap (保持顺序)

 Map result = map.entrySet().stream()
 .sorted(Map.Entry.comparingByKey())
 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
 (oldValue, newValue) -> oldValue, LinkedHashMap::new));

 //默认情况下, Collectors.toMap 将返回一个 HashMap
 */
public class MapSortTest {

    public static void main(String[] args) {
        Map<String, Integer> unsortMap = new HashMap<>();
        unsortMap.put("z", 10);
        unsortMap.put("b", 5);
        unsortMap.put("a", 6);
        unsortMap.put("c", 20);

        //key sort
        Map<String, Integer> result = unsortMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        System.out.println(result);
        Map<String, Integer> result2 = new LinkedHashMap<>();
        unsortMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
        System.out.println(result2);


        //value sort
        //sort by values, and reserve it, 10,9,8,7,6...
        Map<String, Integer> result3 = unsortMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        //Alternative way
        Map<String, Integer> result4 = new LinkedHashMap<>();
        unsortMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> result4.put(x.getKey(), x.getValue()));
        System.out.println(result3);
        System.out.println(result4);

    }
}
