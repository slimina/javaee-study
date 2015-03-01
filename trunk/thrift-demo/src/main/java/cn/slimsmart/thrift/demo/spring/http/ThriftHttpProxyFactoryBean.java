package cn.slimsmart.thrift.demo.spring.http;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

/**
<bean id="standardService"
    class="framework.support.thrift.ThriftHttpProxyFactoryBean">
    <property name="serviceUrl" value="${cs.url}/standardService" />
    <property name="serviceInterface"
        value="api.standard.StandardService$Iface" />
</bean>
http://www.open-open.com/lib/view/open1357804231418.html
 */
public class ThriftHttpProxyFactoryBean<T> extends ThriftClientInterceptor implements FactoryBean{
	
	private HttpClient httpClient; // FactoryBean初始化httpClient 
	
	public ThriftHttpProxyFactoryBean() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager(schemeRegistry);
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
        this.httpClient = new DefaultHttpClient(connectionManager);
        setReadTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);
    }

	@Override
	public T getObject() throws Exception {
		return (T) getServiceProxy(); // 服务对象不在本地，使用代理
	}

	@Override
	public Class<T> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
