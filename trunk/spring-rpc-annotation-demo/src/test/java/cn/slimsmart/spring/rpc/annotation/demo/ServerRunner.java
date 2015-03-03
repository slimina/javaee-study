package cn.slimsmart.spring.rpc.annotation.demo;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 使用jetty作为服务端，运行测试前，先启动startWebapp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml"})
public class ServerRunner {
	private static Server server;

	@BeforeClass
	public static void startWebapp() throws Exception {
		server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(8080); //设置webapp端口
		server.addConnector(connector);
		WebAppContext webAppContext = new WebAppContext();
		//设置工程名称
		webAppContext.setContextPath("/remoting");
		//这是webapp目录
		webAppContext.setWar("src/main/webapp");
		server.setHandler(webAppContext);
		server.start();
		System.out.println("syetem start sucess.");
	}

	@AfterClass
	public static void stopWebapp() throws Exception {
		server.stop();
	}
}
