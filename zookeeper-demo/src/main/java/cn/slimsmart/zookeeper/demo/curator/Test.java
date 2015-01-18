package cn.slimsmart.zookeeper.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

//流式编程风格
public class Test {

	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";

	public static void main(String[] args) throws Exception {
		String path = "/test_path";
		//namespace 指定命名空间，保证唯一性
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(HOSTS).namespace("curator_test")
				.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(20000).build();
		client.start();
		// create a node
		client.create().forPath("/head", new byte[0]);
		// delete a node in background  异步
		client.delete().inBackground().forPath("/head");
		// create a EPHEMERAL_SEQUENTIAL
		client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head", new byte[0]);
		// get the data
		client.getData().watched().inBackground().forPath("/test");
		// check the path exits
		Stat stat = client.checkExists().forPath(path);
		System.out.println(stat);
	}

}
