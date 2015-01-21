package cn.smartslim.mqtt.demo.micro;

import com.ibm.micro.client.mqttv3.MqttCallback;
import com.ibm.micro.client.mqttv3.MqttDeliveryToken;
import com.ibm.micro.client.mqttv3.MqttException;
import com.ibm.micro.client.mqttv3.MqttMessage;
import com.ibm.micro.client.mqttv3.MqttTopic;

/**
 *  消息订阅相关的回调类使用 
 * 必须实现MqttCallback的接口并实现对应的相关接口方法 
 *
 */
public class MQTTClientCallBack implements MqttCallback {

	public void connectionLost(Throwable cause) {
		 System.out.println("Connection lost " +cause.getMessage() + "\" Reason code "   
	                + ((MqttException)cause).getReasonCode() + "\" Cause \""   
	                + ((MqttException)cause).getCause() +  "\"");      
	            cause.printStackTrace();  
	}

	public void deliveryComplete(MqttDeliveryToken token) {
		  System.out.println("Delivery token \"" + token.hashCode());  
	}

	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		System.out.println("Message arrived: \"" + message.toString()  
                + "\" on topic \"" + topic.toString());  
	}  

}
