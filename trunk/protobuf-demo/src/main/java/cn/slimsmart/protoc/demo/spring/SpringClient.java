package cn.slimsmart.protoc.demo.spring;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.slimsmart.protoc.demo.rpc.Message;
import cn.slimsmart.protoc.demo.rpc.Message.Msg;
import cn.slimsmart.protoc.demo.rpc.Message.ReplyService;

import com.google.protobuf.RpcCallback;
import com.googlecode.protobuf.pro.duplex.CleanShutdownHandler;
import com.googlecode.protobuf.pro.duplex.ClientRpcController;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.RpcClientChannel;
import com.googlecode.protobuf.pro.duplex.RpcConnectionEventNotifier;
import com.googlecode.protobuf.pro.duplex.client.DuplexTcpClientPipelineFactory;
import com.googlecode.protobuf.pro.duplex.client.RpcClientConnectionWatchdog;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.googlecode.protobuf.pro.duplex.listener.RpcConnectionEventListener;
import com.googlecode.protobuf.pro.duplex.util.RenamingThreadFactoryProxy;

public class SpringClient {
	
	private static RpcClientChannel channel = null;

	int port;
	String host;

	protected final Log log = LogFactory.getLog(getClass());

	private DuplexTcpClientPipelineFactory clientFactory;

	public SpringClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@PostConstruct
	public void init() {
		runClient();
	}

	public void runClient() {
		PeerInfo client = new PeerInfo();
		PeerInfo server = new PeerInfo(host, port);
		clientFactory = new DuplexTcpClientPipelineFactory();
		clientFactory.setClientInfo(client);
		RpcServerCallExecutor rpcExecutor = new ThreadPoolCallExecutor(3, 10);
		clientFactory.setRpcServerCallExecutor(rpcExecutor);
		
		RpcConnectionEventNotifier rpcEventNotifier = new RpcConnectionEventNotifier();

		final RpcConnectionEventListener listener = new RpcConnectionEventListener() {

			@Override
			public void connectionReestablished(RpcClientChannel clientChannel) {
				log.info("connectionReestablished " + clientChannel);
				channel = clientChannel;
			}

			@Override
			public void connectionOpened(RpcClientChannel clientChannel) {
				log.info("connectionOpened " + clientChannel);
				channel = clientChannel;
			}

			@Override
			public void connectionLost(RpcClientChannel clientChannel) {
				log.info("connectionLost " + clientChannel);
			}

			@Override
			public void connectionChanged(RpcClientChannel clientChannel) {
				log.info("connectionChanged " + clientChannel);
				channel = clientChannel;
			}
		};
		rpcEventNotifier.addEventListener(listener);
		clientFactory.registerConnectionEventListener(rpcEventNotifier);
		
		Bootstrap bootstrap = new Bootstrap();
		EventLoopGroup workers = new NioEventLoopGroup(16, new RenamingThreadFactoryProxy("workers", Executors.defaultThreadFactory()));

		bootstrap.group(workers);
		bootstrap.handler(clientFactory);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
		bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
		bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
		
		RpcClientConnectionWatchdog watchdog = new RpcClientConnectionWatchdog(clientFactory, bootstrap);
		rpcEventNotifier.addEventListener(watchdog);
		watchdog.start();
		
		CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();
		shutdownHandler.addResource(workers);
		shutdownHandler.addResource(rpcExecutor);

		try {
			clientFactory.peerWith(server, bootstrap);
			
			ReplyService.Interface nonReplyService = ReplyService.newStub(channel);
			final ClientRpcController controller = channel.newRpcController();
			controller.setTimeoutMs(0);
			Msg msg = Msg.newBuilder().setContent("client message.....").build();
			//异步发送  服务器反馈RpcCallback
			nonReplyService.call(controller, msg, new RpcCallback<Message.Msg>() {
				@Override
				public void run(Message.Msg msg) {
					System.out.println("receive data:"+msg.getContent());
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
