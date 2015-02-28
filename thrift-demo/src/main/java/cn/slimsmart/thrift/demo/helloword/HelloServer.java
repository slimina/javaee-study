package cn.slimsmart.thrift.demo.helloword;

import org.apache.thrift.TProcessor;

/**
 * 注册服务端
 *
 */
public class HelloServer {
	//注册端口
	public static final int SERVER_PORT = 8080;
	public static void main(String[] args) {
		TProcessor tprocessor = new HelloWord.Processor<HelloWord.Iface>(new HelloWordImpl());

		
	}

}
