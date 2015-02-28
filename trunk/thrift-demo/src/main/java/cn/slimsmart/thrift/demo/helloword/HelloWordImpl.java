package cn.slimsmart.thrift.demo.helloword;

import org.apache.thrift.TException;

/**
 * HelloWord 接口实现类
 *
 */
public class HelloWordImpl implements  HelloWord.Iface{
	@Override
	public String sayHello(String username) throws TException {
		return "hello word, "+username;
	}
}
