package cn.smartslim.mqtt.demo.micro;

import com.ibm.micro.client.mqttv3.MqttClient;
import com.ibm.micro.client.mqttv3.MqttConnectOptions;
import com.ibm.micro.client.mqttv3.MqttException;
import com.ibm.micro.client.mqttv3.MqttSecurityException;


public class ClientSubscribe {
	public static final String TOPIC = "tokudu/yzq124";
	
	 public static void main(String[] args) throws InterruptedException, MqttSecurityException, MqttException {  
		//创建MQTT客户端对象  
         MqttClient client = new MqttClient("tcp://192.168.36.102:1883", "ClientSubscribe");  
         //创建客户端MQTT回调类  
         MQTTClientCallBack callback = new MQTTClientCallBack();  
         //设置MQTT回调  
         client.setCallback(callback);  
         //创建一个连接对象  
         MqttConnectOptions conOptions = new MqttConnectOptions();  
         //设置清除会话信息  
         conOptions.setCleanSession(false);  
           
         //设置超时时间  
         conOptions.setConnectionTimeout(10000);  
           
         //设置会话心跳时间  
         conOptions.setKeepAliveInterval(20000);  
           
         //设置最终端口的通知消息  
         conOptions.setWill(client.getTopic(TOPIC), "the client will stop !".getBytes(), 1, false);  
           
         //连接broker  
         client.connect(conOptions);  
       
         //订阅相关的主题信息  
         client.subscribe(TOPIC, 1);  
           
         Thread.sleep(100000000000000l);  
         //关闭相关的MQTT连接  
         if(client.isConnected()){  
             client.disconnect();  
         }  
         System.out.println("Finished");  
	 }

}
