package cn.slimsmart.thrift.demo.helloworld;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * 多线程Half-sync/Half-async的服务模型.
 * 需要指定为： TFramedTransport 数据传输的方式。
 */
public class HelloTThreadedSelectorServer {
	
	// 注册端口
	public static final int SERVER_PORT = 8080;

	public static void main(String[] args) throws TException {
		
		TProcessor tprocessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldImpl());
		// 传输通道 - 非阻塞方式  
		TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);
		
		//多线程半同步半异步
		TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
		tArgs.processor(tprocessor);
		tArgs.transportFactory(new TFramedTransport.Factory());
		//二进制协议
		tArgs.protocolFactory(new TBinaryProtocol.Factory());
		// 多线程半同步半异步的服务模型
		TServer server = new TThreadedSelectorServer(tArgs);
		System.out.println("HelloTThreadedSelectorServer start....");
		server.serve(); // 启动服务
		
	}

}
