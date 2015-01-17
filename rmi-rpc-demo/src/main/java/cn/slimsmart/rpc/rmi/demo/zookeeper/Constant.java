package cn.slimsmart.rpc.rmi.demo.zookeeper;

public interface Constant {

	String ZK_CONNECTION_STRING = "192.168.100.90:2181,192.168.110.71:2181";
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";

}
