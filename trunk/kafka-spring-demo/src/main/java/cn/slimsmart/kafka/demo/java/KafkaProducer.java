package cn.slimsmart.kafka.demo.java;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

//生产者
public class KafkaProducer {
	
    public final static String TOPIC = "TEST-TOPIC";

	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		//此处配置的是kafka的端口，多个服务器以,分割
		properties.put("metadata.broker.list", "192.168.36.217:9092");
		//配置value的序列化类
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		//配置key的序列化类
		properties.put("key.serializer.class", "kafka.serializer.StringEncoder");
		//request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
		properties.put("request.required.acks","1");
		
		ProducerConfig producerConfig = new ProducerConfig(properties);
		Producer<String, String> producer = new Producer<String, String>(
				producerConfig);

		int messageNo = 1000;
        final int COUNT = 10000;
 
        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            producer.send(new KeyedMessage<String, String>(TOPIC, key ,data));
            System.out.println(data);
            messageNo ++;
        }
		Thread.sleep(1000);

		producer.close();
	}

}
