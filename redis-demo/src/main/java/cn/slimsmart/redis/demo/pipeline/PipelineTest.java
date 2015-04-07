package cn.slimsmart.redis.demo.pipeline;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@SuppressWarnings("resource")
public class PipelineTest {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		usePipeline();
		long end = System.currentTimeMillis();
		System.out.println("usePipeline:"+(end - start));

		start = System.currentTimeMillis();
		withoutPipeline();
		end = System.currentTimeMillis();
		System.out.println("withoutPipeline:"+(end - start));
	}

	private static void withoutPipeline() {
		try {
			Jedis jedis = new Jedis("192.168.36.189", 6379);
			for (int i = 0; i < 1000; i++) {
				jedis.incr("test2");
			}
			jedis.disconnect();
		} catch (Exception e) {
		}
	}

	private static void usePipeline() {
		try {
			Jedis jedis = new Jedis("192.168.36.189", 6379);
			Pipeline pipeline = jedis.pipelined();
			for (int i = 0; i < 1000; i++) {
				pipeline.incr("test2");
			}
			pipeline.sync();
			jedis.disconnect();
		} catch (Exception e) {
		}
	}
}
