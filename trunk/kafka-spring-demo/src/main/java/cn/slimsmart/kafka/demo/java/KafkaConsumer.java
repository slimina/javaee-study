package cn.slimsmart.kafka.demo.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

//消费者
public class KafkaConsumer {
	
	public final static String TOPIC = "TEST-TOPIC";

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		//zookeeper 配置
		properties.put("zookeeper.connect","192.168.36.217:2181");
		properties.put("zookeeper.connectiontimeout.ms", "10000");
		//创建session比较耗时，若创建失败，将其值设置更大，默认6000
		properties.put("zookeeper.session.timeout.ms", "10000");
		properties.put("zookeeper.sync.time.ms", "200"); 
		
		properties.put("auto.commit.enable", "true");
		properties.put("auto.commit.interval.ms", "60000");
		properties.put("auto.offset.reset", "smallest");
		//消费段分组做主备，启动多个只有一个收消息
		properties.put("group.id", "jd-group");
		 //序列化类
		properties.put("serializer.class", "kafka.serializer.StringEncoder");
		
	    //每次最少接收的字节数，默认是1  
	    //props.put("fetch.min.bytes", "1024");  
	    //每次最少等待时间，默认是100  
	    //props.put("fetch.wait.max.ms", "600000");  
	    
		
		ConsumerConfig consumerConfig = new ConsumerConfig(properties);

		ConsumerConnector javaConsumerConnector = Consumer
				.createJavaConsumerConnector(consumerConfig);

		// 消费消息
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(KafkaConsumer.TOPIC, new Integer(1));
 
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
 
        Map<String, List<KafkaStream<String, String>>> consumerMap =
        		javaConsumerConnector.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(KafkaConsumer.TOPIC).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext())
            System.out.println("receive:" +it.next().message());
	}
}
