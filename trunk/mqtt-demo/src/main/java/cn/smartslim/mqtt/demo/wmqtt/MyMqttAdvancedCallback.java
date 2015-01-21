package cn.smartslim.mqtt.demo.wmqtt;

import com.ibm.mqtt.MqttAdvancedCallback;

//MqttAdvancedCallback 高级callback的实现
public class MyMqttAdvancedCallback implements MqttAdvancedCallback {

	public void connectionLost() throws Exception {
		 System.out.println("客户机和broker已经断开");  
	}

	public void publishArrived(String topicName, byte[] payload, int Qos, boolean retained) throws Exception {
		System.out.println("订阅主题: " + topicName);
		System.out.println("消息数据: " + new String(payload));
		System.out.println("消息级别(0,1,2): " + Qos);
		System.out.println("是否是实时发送的消息(false=实时，true=服务器上保留的最后消息): " + retained);
	}

	public void published(int arg0) {
		System.out.println("发布成功");
	}

	public void subscribed(int Qos, byte[] payload) {
		System.out.println("消息数据: " + new String(payload));
		System.out.println("消息级别(0,1,2): " + Qos);
	}

	public void unsubscribed(int arg0) {
		System.out.println("取消订阅");
	}

}
