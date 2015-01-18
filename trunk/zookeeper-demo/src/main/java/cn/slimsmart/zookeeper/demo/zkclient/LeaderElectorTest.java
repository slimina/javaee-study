package cn.slimsmart.zookeeper.demo.zkclient;

import org.apache.zookeeper.ZooDefs.Ids;
import org.menagerie.DefaultZkSessionManager;
import org.menagerie.ZkSessionManager;
import org.menagerie.election.LeaderElector;
import org.menagerie.election.ZkLeaderElector;

public class LeaderElectorTest {

	public static void main(String[] args) {
		for(int i=0 ;i<5;i++){
			new Thread(new myRunable("client-"+i)).start();
		}
	}
}

class myRunable implements Runnable{
	String name = "";
	
	public myRunable(String name ){
		this.name = name;
	}

	@Override
	public void run() {
		ZkSessionManager zksm = new DefaultZkSessionManager("192.168.36.54:2181,192.168.36.99:2181,192.168.36.189:2181", 5000);
		LeaderElector elector = new ZkLeaderElector("/leaderElectionTest", zksm, Ids.OPEN_ACL_UNSAFE);
		if (elector.nominateSelfForLeader()) {
			System.out.println(name+":"+"Try to become the leader success!");
		}else{
			System.out.println(name+":"+"Try to become the leader fail!");
		}
		
		
	}
}