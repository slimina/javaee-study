package cn.slimsmart.java8.stream;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * stream close
 */
public class StreamCloseTest {

    public static void main(String[] args) {
        //在 Java 8, Stream 不能被重新使用, 一旦它被使用或者消费了, stream 将被关闭.
        String[] array = {"a", "b", "c", "d", "e"};
        Stream<String> stream = Arrays.stream(array);
        // loop a stream
        stream.forEach(x -> System.out.println(x));
        try {
            // reuse it to filter again! throws IllegalStateException
            long count = stream.filter(x -> "b".equals(x)).count(); //跑出异常
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }

        //重新运用一个 stream
        //使用 Supplier
        Supplier<Stream<String>> supplier = ()->Stream.of(array);
        supplier.get().forEach(x -> System.out.println(x));
        //get another new stream
        long count = supplier.get().filter(x -> "b".equals(x)).count();
        System.out.println(count);


    }
}
