package cn.slimsmart.protoc.demo.rpc.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.slimsmart.protoc.demo.rpc.Message.Msg;
import cn.slimsmart.protoc.demo.rpc.Message.ReplyService;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * 异步反馈服务实现
 */
public class NonBlockReplyService implements ReplyService.Interface{
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void call(RpcController controller, Msg request, RpcCallback<Msg> done) {
		log.debug("接收反馈消息:"+request.getContent());
		if ( controller.isCanceled() ) {
			done.run(null);
			return;
		}
		done.run(Msg.newBuilder().setContent("收到反馈成功.").build());
	}
}
