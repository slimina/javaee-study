package cn.slimsmart.thrift.demo.helloworld;

import org.apache.thrift.TException;

/**
 * HelloWord 接口实现类
 *
 */
public class HelloWorldImpl implements  HelloWorld.Iface{
	@Override
	public String sayHello(String username) throws TException {
		return "hello word, "+username;
	}
}
