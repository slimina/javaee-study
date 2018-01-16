package cn.slimsmart.java.lambda.inner;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 在单一的输入参数上需要进行的操作。和Function不同的是，Consumer没有返回值(消费者，有输入，无输出)
 */
public class ConsumerTest {

    public static void main(String[] args) {
        Consumer<StringBuffer> consumer = (s)->{
            System.out.println(s.append("aaa"));
        };
        //consumer.accept(new StringBuffer("hh"));
        consumer = consumer.andThen((s)->{
            System.out.println(s.append("bb"));
        });
        
        Supplier<StringBuffer> str = StringBuffer::new;
        
        System.out.println(str.get().hashCode());
        System.out.println(str.get().hashCode());
    }
}
