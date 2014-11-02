package cn.slimsmart.kafka.demo;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProductor {

	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		properties.put("zk.connect", "192.168.36.127:2181,192.168.36.91:2181");
		properties.put("metadata.broker.list", "192.168.36.127:9092,192.168.36.91:9092,192.168.36.91:9092");
		properties.put("zk.connectiontimeout.ms", "10000");
		properties.put("serializer.class", "kafka.serializer.StringEncoder");

		ProducerConfig producerConfig = new ProducerConfig(properties);
		Producer<String, String> producer = new Producer<String, String>(
				producerConfig);

		// 构建消息体
		KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
				"test-topic", "test-message");
		producer.send(keyedMessage);

		Thread.sleep(1000);

		producer.close();
	}

}
