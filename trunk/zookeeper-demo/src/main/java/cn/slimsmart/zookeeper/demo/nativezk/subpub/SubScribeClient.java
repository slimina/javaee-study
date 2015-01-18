package cn.slimsmart.zookeeper.demo.nativezk.subpub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SubScribeClient implements Watcher
{
	private ZooKeeper zk;
	private boolean connected;
	private boolean subscribed;
	private String hostStr;
	private int timeOut;
	
	private ConcurrentHashMap<String,byte[]> childNodes=new ConcurrentHashMap<String,byte[]>();
	
	private String path;
	
	public SubScribeClient(String hostStr,int timeOut,String path)
	{
		this.hostStr=hostStr;
		this.timeOut=timeOut;
		this.path=path;
		try
		{
			zk=new ZooKeeper(hostStr,timeOut,this);
			connected=true;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event)
	{
		if(event.getState().equals(KeeperState.Disconnected))
		{
			connected=false;
		}
		if(event.getState().equals(KeeperState.SyncConnected))
		{	
			if(!subscribed&&!connected)
			{
				subScribe();
			}
			connected=true;
		}
		if(event.getState().equals(KeeperState.Expired))
		{
			try
			{
				close();
				subscribed=false;
				zk=new ZooKeeper(hostStr,timeOut,this);
				connected=true;
				subScribe();
			
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void subScribe()
	{
		zk.getChildren(path, new SubScribeWatcher(this), new SubScribeCallBack(this), childNodes);
	}
	
	private void getData(String path)
	{
		zk.getData(path, false, new DataCallBack(), childNodes);
	}
	
	public void close()
	{
		try
		{
			zk.close();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class SubScribeCallBack implements AsyncCallback.Children2Callback
	{
		private SubScribeClient client;
		
		public SubScribeCallBack(SubScribeClient client)
		{
			this.client = client;
		}

		@Override
		public void processResult(int rc, String path, Object ctx,
				List<String> children, Stat stat)
		{
			ConcurrentHashMap<String,byte[]> childNodes=(ConcurrentHashMap<String,byte[]>) ctx;
			if(rc==0)
			{
				List<String> list=new ArrayList<String>();
				
				for(String s:children)
				{
					if(path.equals("/"))
					{
						s="/"+s;
					}
					else
					{
						s=path+"/"+s;
					}
					
					list.add(s);
				}				
				for(String ss:childNodes.keySet())
				{
					if(!list.contains(ss))
					{
						childNodes.remove(ss);
					}
				}
				for(String s:list)
				{
					if(!childNodes.containsKey(s))
					{
						client.getData(s);
						
					}
				}
				
				client.subscribed=true;
			}
			
		}
		
	}
	
	private static class SubScribeWatcher implements Watcher
	{
		private SubScribeClient client;
		
		public SubScribeWatcher(SubScribeClient client)
		{
			this.client = client;
		}

		@Override
		public void process(WatchedEvent event)
		{
			System.out.println("SubScribeWatcher event: "+event);
			
			if(event.getType().equals(EventType.NodeChildrenChanged))
			{
				client.subScribe();
				
			}
			
		}
		
	}
	
	private static class DataCallBack implements AsyncCallback.DataCallback
	{
		@Override
		public void processResult(int rc, String path, Object ctx, byte[] data,
				Stat stat)
		{
			if(rc==0)
			{
				ConcurrentHashMap<String,byte[]> map=(ConcurrentHashMap<String, byte[]>) ctx;
				map.put(path, data);
			}
			
		}
		
	}

}
