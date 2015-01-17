package cn.slimsmart.rpc.rmi.demo.rmi;

import java.rmi.Naming;

public class RmiClient {

	public static void main(String[] args) throws Exception {
		String url = "rmi://localhost:1099/"+HelloService.class.getName();
		HelloService helloService = (HelloService) Naming.lookup(url);
		String result = helloService.sayHello("Jack");
		System.out.println(result);
	}
}
