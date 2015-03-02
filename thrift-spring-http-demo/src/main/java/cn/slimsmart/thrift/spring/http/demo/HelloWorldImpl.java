package cn.slimsmart.thrift.spring.http.demo;

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
}
