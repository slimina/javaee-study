package cn.slimsmart.java.lambda.demo;

import java.util.stream.Stream;

/**
 *
 */
public class Test6 {

    public static void main(String[] args) {
        Stream.generate(Math::random).map(s-> s*100).filter(s->(s.intValue())%2==0).limit(10).forEach(System.out::println);
    }
}
