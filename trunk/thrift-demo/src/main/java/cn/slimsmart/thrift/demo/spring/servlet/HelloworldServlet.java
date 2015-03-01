package cn.slimsmart.thrift.demo.spring.servlet;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServlet;

import cn.slimsmart.thrift.demo.helloworld.HelloWorld;

/**
 * 服务端TServlet
 */
public class HelloworldServlet extends TServlet {

	private static final long serialVersionUID = 1L;

	public HelloworldServlet(HelloWorld.Iface helloWorldImpl) {
		super(new HelloWorld.Processor<HelloWorld.Iface>(helloWorldImpl) , new TCompactProtocol.Factory());
	}
}
