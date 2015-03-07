package cn.slimsmart.thrift.demo.helloworld;

import org.apache.thrift.TException;

/**
 * HelloWord 接口实现类
 *
 */
public class HelloWorldImpl implements  HelloWorld.Iface{
	@Override
	public String sayHello(String username) throws TException {
		return "hello world, "+username;
	}
	
	public static void main(String[] aa){
		System.out.println(HelloWorldImpl.class.getInterfaces()[0].getEnclosingClass().getName());
	}
}
