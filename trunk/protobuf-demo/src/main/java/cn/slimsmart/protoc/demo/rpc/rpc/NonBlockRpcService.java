package cn.slimsmart.protoc.demo.rpc.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.pro.duplex.ClientRpcController;
import com.googlecode.protobuf.pro.duplex.RpcClientChannel;
import com.googlecode.protobuf.pro.duplex.execute.ServerRpcController;

import cn.slimsmart.protoc.demo.rpc.Message;
import cn.slimsmart.protoc.demo.rpc.Message.Msg;
import cn.slimsmart.protoc.demo.rpc.Message.ReplyService;
import cn.slimsmart.protoc.demo.rpc.Message.Request;
import cn.slimsmart.protoc.demo.rpc.Message.Response;
import cn.slimsmart.protoc.demo.rpc.Message.RpcService;

public class NonBlockRpcService implements RpcService.Interface{
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void call(RpcController controller, Request request, RpcCallback<Response> done) {
		log.info("接收到数据：");
		log.info("serviceName : "+request.getServiceName());
		log.info("methodName : "+request.getMethodName());
		log.info("params : "+request.getParams());
		if ( controller.isCanceled() ) {
			done.run(null);
			return;
		}
		
		RpcClientChannel channel = ServerRpcController.getRpcChannel(controller);
		ReplyService.Interface clientService = ReplyService.newStub(channel);
		ClientRpcController clientController = channel.newRpcController();
		clientController.setTimeoutMs(3000);
		
		Msg msg = Msg.newBuilder().setContent("success.").build();
		clientService.call(clientController, msg, new RpcCallback<Message.Msg>(){
			@Override
			public void run(Message.Msg msg) {
				log.info("回调收到消息："+msg.getContent());
			}
		});
		Response response = Response.newBuilder().setCode(0).setMsg("处理完成").setData("server hello").build();
		done.run(response);
	}

}
