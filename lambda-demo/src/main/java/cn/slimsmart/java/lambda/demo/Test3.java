package cn.slimsmart.java.lambda.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 集合操作
 */
public class Test3 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.forEach((x)-> System.out.println(x));
        list.removeIf((x)-> x%2==0);
        list.forEach(System.out::println);//去掉偶数

        //Map集合
        Map<Integer, String> map = new HashMap<>();
        map.put(12,"aaa");
        map.put(null,"bbb");
        map.put(13,"ccc");
        
        //转换成Collection
        System.out.println(map.entrySet().stream().filter(e->e.getKey()!=null).collect(Collectors.toMap(e->((Map.Entry<Integer,String>)e).getKey(), e->((Map.Entry<Integer,String>)e).getValue())));;
        
        map.putIfAbsent(null,"ddd");//已经存在返回当前值，不覆盖
        map.forEach((key,value)->{
            System.out.println(key+":"+value);
        });

        System.out.println(map.computeIfPresent(null,(key,value)->key+value));
        System.out.println(map.computeIfAbsent(13,key->"111"+key));
        map.forEach((key,value)->{
            System.out.println(key+":"+value);
        });
        //合并操作先看map中是否没有特定的key/value存在，如果是，则把key/value存入map，
        // 否则merging函数就会被调用，对现有的数值进行修改。
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.merge(9, "val10", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9));
        
    }
}
