package cn.slimsmart.java.lambda.inner;

import java.util.function.Function;
import java.util.function.LongToDoubleFunction;

//功能接口(Functions)Functions接受一个参数并产生一个结果,默认方法能够用于将多个函数链接在一起。
public class FunctionTest {

    public static void main(String[] args) {
        Function<Integer,Integer> fun =(n)->n*2;
        System.out.println(fun.apply(12));
        fun = fun.andThen((n)->n/3);
        System.out.println(fun.apply(12));

        LongToDoubleFunction fun1 = (n)-> n*0.1;
        System.out.println(fun1.applyAsDouble(1L));
    }
}
