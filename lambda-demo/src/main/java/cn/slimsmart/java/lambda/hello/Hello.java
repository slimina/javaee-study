package cn.slimsmart.java.lambda.hello;

@FunctionalInterface
public interface Hello{
	String sayHello(String name);
    //默认方法实现
	default void print(String name){System.out.println(name);}
    //静态方法实现
    static int add(int a,int b){return a+b;}
    //Object 默认方法
    String toString();
}
