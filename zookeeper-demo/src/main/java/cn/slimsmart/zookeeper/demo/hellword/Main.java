package cn.slimsmart.zookeeper.demo.hellword;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class Main {

	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		ZooKeeperClient client = new ZooKeeperClient("192.168.100.90:2181,192.168.110.71:2181",5000);
		client.createConnection();
		client.createPath("/slimsmart_registry", "");
	}
}
