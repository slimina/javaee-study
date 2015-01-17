package cn.slimsmart.rpc.rmi.demo.zookeeper;

import cn.slimsmart.rpc.rmi.demo.rmi.HelloService;
import cn.slimsmart.rpc.rmi.demo.rmi.HelloServiceImpl;

public class Server {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
        ServiceProvider provider = new ServiceProvider();
        HelloService helloService = new HelloServiceImpl();
        provider.publish(helloService, "127.0.0.1", 6003);
        Thread.sleep(Long.MAX_VALUE);
	}
}
