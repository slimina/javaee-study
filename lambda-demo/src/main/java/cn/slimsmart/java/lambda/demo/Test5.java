package cn.slimsmart.java.lambda.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * parallel Stream
 */
public class Test5 {

    public static void main(String[] args) {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        //对这个集合进行排序所使用的时间

        long time = System.currentTimeMillis();
        //串行
        long count = values.stream().sorted().count();
        //并行 或.stream().parallel()
        //long count = values.parallelStream().sorted().count();
        System.out.println(count);
        System.out.println("cost time :"+(System.currentTimeMillis()-time)+"ms");
    }
}
