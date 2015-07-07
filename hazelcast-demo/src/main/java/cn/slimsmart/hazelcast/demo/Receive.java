package cn.slimsmart.hazelcast.demo;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class Receive {

	public static void main(String[] args) {
		ClientNetworkConfig netConfig = new ClientNetworkConfig();
		netConfig.addAddress("127.0.0.1:5701","127.0.0.1:5702");
		ClientConfig clientConfig = new ClientConfig();
        clientConfig.setNetworkConfig(netConfig);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        ITopic mytopic= client.getTopic("mytopic"); 
        mytopic.addMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				System.out.println(message.getMessageObject());
			}
		});
	}
}
