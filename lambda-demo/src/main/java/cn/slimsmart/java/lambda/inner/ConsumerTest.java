package cn.slimsmart.java.lambda.inner;

import java.util.function.Consumer;

/**
 * 在单一的输入参数上需要进行的操作。和Function不同的是，Consumer没有返回值(消费者，有输入，无输出)
 */
public class ConsumerTest {

    public static void main(String[] args) {
        Consumer<Integer> consumer = (n)->{
            System.out.println(n*n);
        };
        consumer.accept(10);
        consumer = consumer.andThen((n)->{
            System.out.println(n/2);
        });
        consumer.accept(10);
    }
}
