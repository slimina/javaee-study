package cn.slimsmart.kafka.demo;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class KafkaConsumer {

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.put("zookeeper.connect",
				"192.168.36.127:2181,192.168.36.91:2181");
		properties.put("auto.commit.enable", "true");
		properties.put("auto.commit.interval.ms", "60000");
		//消费段分组做主备，启动多个只有一个收消息
		properties.put("group.id", "test-group");
		properties.put("zookeeper.connectiontimeout.ms", "10000");
		properties.put("zookeeper.session.timeout.ms", "60000");
		properties.put("zookeeper.sync.time.ms", "200"); 
		
	    //每次最少接收的字节数，默认是1  
	    //props.put("fetch.min.bytes", "1024");  
	    //每次最少等待时间，默认是100  
	    //props.put("fetch.wait.max.ms", "600000");  
	    
		
		ConsumerConfig consumerConfig = new ConsumerConfig(properties);

		ConsumerConnector javaConsumerConnector = Consumer
				.createJavaConsumerConnector(consumerConfig);

		// topic的过滤器
		Whitelist whitelist = new Whitelist("test-topic");
		List<KafkaStream<byte[], byte[]>> partitions = javaConsumerConnector
				.createMessageStreamsByFilter(whitelist);

		if (partitions==null ||partitions.size() ==0) {
			System.out.println("empty!");
			TimeUnit.SECONDS.sleep(1);
		}

		// 消费消息
		for (KafkaStream<byte[], byte[]> partition : partitions) {

			ConsumerIterator<byte[], byte[]> iterator = partition.iterator();
			while (iterator.hasNext()) {
				MessageAndMetadata<byte[], byte[]> next = iterator.next();
				System.out.println("partiton:" + next.partition());
				System.out.println("offset:" + next.offset());
				System.out.println("message:"
						+ new String(next.message(), "utf-8"));
			}

		}
	}

}
