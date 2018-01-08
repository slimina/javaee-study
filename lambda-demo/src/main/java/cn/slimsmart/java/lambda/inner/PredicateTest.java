package cn.slimsmart.java.lambda.inner;

import java.util.function.Predicate;

//断言接口(Predicates) 是只拥有一个参数的Boolean型功能的接口。
// 这个接口拥有多个默认方法用于构成predicates复杂的逻辑术语
public class PredicateTest {

    public static void main(String[] args) {
        Predicate<String> predicate = (s)->s.length() < 5;
        System.out.println(predicate.test("test"));
        //反面
        System.out.println(predicate.negate().test("test"));

        predicate = predicate.and(s -> s.endsWith("abc"));

        System.out.println(predicate.test("123abc"));
    }
}
