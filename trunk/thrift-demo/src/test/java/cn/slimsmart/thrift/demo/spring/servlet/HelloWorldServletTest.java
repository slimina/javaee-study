package cn.slimsmart.thrift.demo.spring.servlet;

import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransportException;

import cn.slimsmart.thrift.demo.helloworld.HelloWorld;

public class HelloWorldServletTest {

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/thrift-demo/helloworldServlet.do";
		
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		// Disable Expect-Continue
		params.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		// Enable staleness check
		params.setParameter("http.connection.stalecheck", true);
		HttpConnectionParams.setSoTimeout(params, 10000); // 10 secondes
		HttpConnectionParams.setConnectionTimeout(params, 10000); // 10 secondes

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 8080, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		BasicClientConnectionManager cm = new BasicClientConnectionManager(schemeRegistry);
		THttpClient thc = null;
		try {
			thc = new THttpClient(url, new DefaultHttpClient(cm, params));
			TProtocol loPFactory = new TCompactProtocol(thc);
			HelloWorld.Client client = new HelloWorld.Client(loPFactory);
			System.out.println(client.sayHello("Jack"));
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}

}
