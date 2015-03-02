package cn.slimsmart.thrift.spring.http.remote;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.http.client.HttpClient;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;

public class ThriftClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor {

	private ThriftProxyFactory proxyFactory = new ThriftProxyFactory();
	private Object thriftProxy;

	private HttpClient httpClient;
	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

	public void setProxyFactory(ThriftProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory == null ? new ThriftProxyFactory() : proxyFactory;
	}

	protected Object createThriftProxy(ThriftProxyFactory proxyFactory) throws Exception {
		Assert.notNull(getServiceInterface(), "'serviceInterface' is required");
		return proxyFactory.create(getServiceInterface(), getServiceUrl());
	}

	public void prepare() throws RemoteLookupFailureException {
		try {
			proxyFactory.setHttpClient(httpClient);
			this.thriftProxy = createThriftProxy(this.proxyFactory);
		} catch (Exception ex) {
			throw new RemoteLookupFailureException("Service URL [" + getServiceUrl() + "] is invalid", ex);
		}
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (getServiceInterface() == null) {
			throw new IllegalArgumentException("property serviceInterface is required.");
		}
		org.apache.http.conn.scheme.SchemeRegistry schemeRegistry = new org.apache.http.conn.scheme.SchemeRegistry();
		schemeRegistry.register(new org.apache.http.conn.scheme.Scheme("http", 80, org.apache.http.conn.scheme.PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new org.apache.http.conn.scheme.Scheme("https", 443, org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory()));

		org.apache.http.impl.conn.PoolingClientConnectionManager connectionManager = new org.apache.http.impl.conn.PoolingClientConnectionManager(
				schemeRegistry);
		connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
		connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);

		this.httpClient = new org.apache.http.impl.client.DefaultHttpClient(connectionManager);
		setReadTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS);
		prepare();
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] args = invocation.getArguments();
		String name = method.getName();
		if (args.length == 0) {
			if ("toString".equals(name)) {
				return "Thrift proxy for service URL [" + getServiceUrl() + "]";
			} else if ("hashCode".equals(name)) {
				return getServiceUrl().hashCode();
			}
		} else if (args.length == 1 && "equals".equals(name)) {
			return getServiceUrl().equals(args[0]);
		}
		if (this.thriftProxy == null) {
			throw new IllegalStateException("ThriftClientInterceptor is not properly initialized - " + "invoke 'prepare' before attempting any operations");
		}
		ClassLoader originalClassLoader = overrideThreadContextClassLoader();
		try {
			return method.invoke(thriftProxy, args);
		} catch (InvocationTargetException e) {
			logger.error("error:{}", e);
			throw new ThriftServletException("invoke error : {}", e);
		} catch (Throwable ex) {
			logger.error("error:{}", ex);
			throw new ThriftServletException("error : {}", ex);
		} finally {
			resetThreadContextClassLoader(originalClassLoader);
		}
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return this.httpClient;
	}

	public void setConnectTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		getHttpClient().getParams().setIntParameter(org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
	}

	public void setReadTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		getHttpClient().getParams().setIntParameter(org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT, timeout);
	}

}
