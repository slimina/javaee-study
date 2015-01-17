package cn.slimsmart.rpc.rmi.demo.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer {

	public static void main(String[] args) throws Exception{
		int port = 1099;
        String url = "rmi://localhost:1099/"+HelloService.class.getName();
        System.out.println(url);
        LocateRegistry.createRegistry(port);
        Naming.rebind(url, new HelloServiceImpl());
	}

}
