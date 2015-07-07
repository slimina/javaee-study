package cn.slimsmart.hazelcast.demo;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;

public class DistributedLock {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		HazelcastInstance client = null;
		try {
			ClientNetworkConfig netConfig = new ClientNetworkConfig();
			netConfig.addAddress("127.0.0.1:5701","127.0.0.1:5702");
			ClientConfig clientConfig = new ClientConfig();
	        clientConfig.setNetworkConfig(netConfig);
	        client = HazelcastClient.newHazelcastClient(clientConfig);
		    java.util.concurrent.locks.Lock lock = client.getLock("mylock");
		    while (true) {
		        lock.lock();
		        try {
		        	Thread.sleep(3000);
		            System.out.println("do some work involving access to shared resources");
		        } finally {
		            lock.unlock();
		        }
		    }
		} finally {
			if(client!=null){
				client.shutdown();
			}
		}
	}

}
