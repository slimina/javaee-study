package cn.slimsmart.java.lambda.demo;

import java.util.*;
import java.util.function.Supplier;

/**
 * 闭包
 */
public class Test2 {

    interface Callback{
        int inc();
    }

    private int count =0;

    private Callback callback1 = new Callback() {
        @Override
        public int inc() {
            return count++;
        }
    };

    private Callback callback2 = ()->count++;

    public static void main(String[] args) {
        Test2 test = new Test2();
        test.callback1.inc();
        test.callback2.inc();
        System.out.println(test.count);
    }
}
