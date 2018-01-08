package cn.slimsmart.java.lambda.hello;

import java.util.Optional;

/**
 * 选项接口(Optionals)一种特殊的工具用来解决NullPointerException
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("abc");
        System.out.println( optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("test"));

        optional.ifPresent((s)-> System.out.println(s));

        /*
        optional.filter(Predicate<? super T> predicate);
        optional.flatMap(Function<? super T, Optional<U>> mapper);
        optional.map(Function<? super T, Optional<U>> mapper);
        optional.orElseGet(Supplier<? extends T> other);
        optional.orElseThrow(Supplier<? extends X> exceptionSupplier);
        */
    }
}
