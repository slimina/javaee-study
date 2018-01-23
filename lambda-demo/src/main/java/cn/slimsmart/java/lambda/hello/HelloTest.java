package cn.slimsmart.java.lambda.hello;

import java.io.Closeable;
import java.io.IOException;

public class HelloTest {
	//接口匿名实现类
	public static void main(String[] args) throws IOException {
        //线程执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread run ...");
            }
        }).start();
        new Thread(()->{
            System.out.println("thread run ...");
        }).start();
        //java内置默认接口
        Closeable close =()->System.out.println("close");
        close.close();
        //自定义函数接口
		Hello hello =(name)->{
		    return "hello "+ name;
        };
		System.out.println(hello.sayHello("Jack"));
        hello.print("Tom");
        System.out.println( Hello.add(11,22));
        //继承object
        System.out.println(hello.hashCode());

        int a = 100; //默认为final
       // a++; 不可操作
        //1.8 在lambda、匿名类中使用了外部变量,可以不加final，
        // 默认为final 不可更改，建议将final加上提高可读性
        hello =(name)->{
            System.out.println("a = [" + a + "]");
            return "hello "+ name;
        };
        hello.sayHello("TOM");
    }
}
