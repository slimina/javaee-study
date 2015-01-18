package cn.slimsmart.zookeeper.demo.nativezk.crud.hellword;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class Main {

	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		ZooKeeperClient client = new ZooKeeperClient("192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181",5000);
		client.createConnection();
		client.createPath("/slimsmart_registry", "");
	}
}
