package cn.slimsmart.java.lambda.inner;

import java.util.Date;
import java.util.function.Supplier;

// 供应接口(Supplier) 对于给定的泛型类型产生一个实例。不同于Functions，Suppliers不需要任何参数。
//对象实例化
public class SupplierTest {
    public static void main(String[] args) {
        Supplier<String> str =()->"hello".intern();
        System.out.println(str.get());

        Supplier<Date> date = ()->new Date();
        System.out.println(date.get());
    }

}
