package cn.slimsmart.protoc.demo.spring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.slimsmart.protoc.demo.rpc.Message.ReplyService;
import cn.slimsmart.protoc.demo.rpc.rpc.NonBlockReplyService;

import com.google.protobuf.Service;
import com.googlecode.protobuf.pro.duplex.CleanShutdownHandler;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.googlecode.protobuf.pro.duplex.server.DuplexTcpServerPipelineFactory;
import com.googlecode.protobuf.pro.duplex.util.RenamingThreadFactoryProxy;

public class SpringServer {

	@Autowired(required = true)
	private NonBlockReplyService nonBlockReplyService;

	int port;
	String host;

	protected final Log log = LogFactory.getLog(getClass());

	private DuplexTcpServerPipelineFactory serverFactory;

	public SpringServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@PostConstruct
	public void init() {
		runServer();
	}

	public void runServer() {
		PeerInfo serverInfo = new PeerInfo(host, port);
		serverFactory = new DuplexTcpServerPipelineFactory(serverInfo);
		RpcServerCallExecutor rpcExecutor = new ThreadPoolCallExecutor(10, 10);
		serverFactory.setRpcServerCallExecutor(rpcExecutor);
		log.info("Proto Serverbootstrap created");

		// 注册非阻塞服务
		Service replyService = ReplyService.newReflectiveService(nonBlockReplyService);
		serverFactory.getRpcServiceRegistry().registerService(true, replyService);

		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup boss = new NioEventLoopGroup(2, new RenamingThreadFactoryProxy("boss", Executors.defaultThreadFactory()));
		EventLoopGroup workers = new NioEventLoopGroup(2, new RenamingThreadFactoryProxy("worker", Executors.defaultThreadFactory()));
		bootstrap.group(boss, workers);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
		bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);
		bootstrap.childOption(ChannelOption.SO_RCVBUF, 1048576);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 1048576);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.childHandler(serverFactory);
		bootstrap.localAddress(serverInfo.getPort());
		log.info("Proto Ping Server Bound to port " + port);

		CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();
		shutdownHandler.addResource(boss);
		shutdownHandler.addResource(workers);
		shutdownHandler.addResource(rpcExecutor);
		bootstrap.bind();
	}
}
