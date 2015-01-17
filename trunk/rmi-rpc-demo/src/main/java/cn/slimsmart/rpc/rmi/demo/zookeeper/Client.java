package cn.slimsmart.rpc.rmi.demo.zookeeper;

import cn.slimsmart.rpc.rmi.demo.rmi.HelloService;

public class Client {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ServiceConsumer consumer = new ServiceConsumer();
		while (true) {
			HelloService helloService = consumer.lookup();
			String result = helloService.sayHello("Jack");
			System.out.println(result);
			Thread.sleep(3000);
		}
	}
}
