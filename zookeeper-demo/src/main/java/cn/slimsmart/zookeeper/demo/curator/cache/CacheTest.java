package cn.slimsmart.zookeeper.demo.curator.cache;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;

public class CacheTest {
	public static PathChildrenCache pathChildrenCache(CuratorFramework client, String path, Boolean cacheData) throws Exception {
		final PathChildrenCache cached = new PathChildrenCache(client, path, cacheData);
		cached.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				PathChildrenCacheEvent.Type eventType = event.getType();
				switch (eventType) {
				case CONNECTION_RECONNECTED:
					cached.rebuild();
					break;
				case CONNECTION_SUSPENDED:
				case CONNECTION_LOST:
					System.out.println("Connection error,waiting...");
					break;
				default:
					System.out.println("PathChildrenCache changed : {path:" + event.getData().getPath() + " data:" + new String(event.getData().getData())
							+ "}");
				}
			}
		});
		return cached;
	}

	public static NodeCache nodeCache(CuratorFramework client, String path) {
		final NodeCache cache = new NodeCache(client, path);
		cache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				System.out.println("NodeCache changed, data is: " + new String(cache.getCurrentData().getData()));
			}
		});

		return cache;
	}

	public static TreeCache treeCache(CuratorFramework client, String path) {
		final TreeCache cache = new TreeCache(client, path);
		cache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {

				switch (event.getType()) {
				case NODE_ADDED: {
					System.out.println("TreeNode added: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + ", value: "
							+ new String(event.getData().getData()));
					break;
				}
				case NODE_UPDATED: {
					System.out.println("TreeNode changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()) + ", value: "
							+ new String(event.getData().getData()));
					break;
				}
				case NODE_REMOVED: {
					System.out.println("TreeNode removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
					break;
				}
				default:
					System.out.println("Other event: " + event.getType().name());
				}
			}
		});

		return cache;
	}

	private static final String HOSTS = "192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181";

	public static void main(String[] args) throws Exception {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(HOSTS, retryPolicy);
		client.start();

		EnsurePath ensurePath = client.newNamespaceAwareEnsurePath("/create/test");
		ensurePath.ensure(client.getZookeeperClient());

		/**
		 * pathChildrenCache
		 */
		PathChildrenCache cache = pathChildrenCache(client, "/create", true);
		cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		List<ChildData> datas = cache.getCurrentData();

		for (ChildData data : datas) {
			System.out.println("pathcache:{" + data.getPath() + ":" + new String(data.getData()) + "}");
		}

		/**
		 * NodeCache
		 */
		NodeCache nodeCache = nodeCache(client, "/create/test");
		nodeCache.start(true);
		
		TreeCache treeCache = treeCache(client, "/create");
		treeCache.start();
		

		client.setData().forPath("/create/test", "1111".getBytes());
		client.setData().forPath("/create", "2222".getBytes());

		System.out.println(new String(nodeCache.getCurrentData().getData()));

		Thread.sleep(10000);
		CloseableUtils.closeQuietly(cache);
		CloseableUtils.closeQuietly(client);
	}

}
