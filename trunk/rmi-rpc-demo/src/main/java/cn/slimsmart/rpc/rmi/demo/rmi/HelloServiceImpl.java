package cn.slimsmart.rpc.rmi.demo.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

	private static final long serialVersionUID = 1L;

	public HelloServiceImpl() throws RemoteException {
		super();
	}

	public String sayHello(String name) throws RemoteException {
		return String.format("Hello %s", name);
	}
}
