package cn.slimsmart.rabbitmq.demo.rpc;

public class RPCMain {

	public static void main(String[] args) throws Exception {
		RPCClient rpcClient = new RPCClient();
		System.out.println(" [x] Requesting getMd5String(abc)");   
		String response = rpcClient.call("a");
		System.out.println(" [.] Got '" + response + "'");
		rpcClient.close();
	}

}
