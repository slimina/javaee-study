package cn.smartslim.mqtt.demo.queue;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class QueuesTest {
	public static void main(String[] args) {
		//异步写入，关闭事务
		DB db = DBMaker.newFileDB(new File("mapdb.db"))
				.closeOnJvmShutdown().transactionDisable()
				.asyncWriteFlushDelay(100).encryptionEnable("abc123").make();
		BlockingQueue<Object> queue = (BlockingQueue<Object>) db.getQueue("queue");
		queue.add("abc");
		queue.add("edf");
		System.out.println(queue);
	}
}
