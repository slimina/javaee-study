package cn.slimsmart.java.lambda.demo;

import static  cn.slimsmart.java.lambda.hello.ComparatorTest.Person;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 方法引用
 */
public class Test1 {

    @FunctionalInterface
    interface Factory<T> {
        T create(String firstName,String lastName);
    }
    public static void main(String[] args) {

        Integer[] arr = new Integer[] { 2, 3, 4, 5, 6, 73, 2, 3 };
        Arrays.sort(arr, Integer::compare);
        System.out.println(Arrays.toString(arr));

        //静态方法引用
        Function<Boolean,String> fun = String::valueOf;
        System.out.println(fun.apply(true));

        //构造方法引用
        Factory<Person> factory = Person::new;
        Person p1 = factory.create("aaa","bbb");
        System.out.println(p1);

        Consumer<String> consumer = p1::setFirstName;
        consumer.accept("123");
        System.out.println(p1);
    }
}
