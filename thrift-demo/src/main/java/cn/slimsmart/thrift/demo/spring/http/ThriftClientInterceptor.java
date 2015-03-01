package cn.slimsmart.thrift.demo.spring.http;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

public class ThriftClientInterceptor extends UrlBasedRemoteAccessor implements MethodInterceptor{
	
	private Object serviceProxy;


	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		if (getServiceInterface() == null) {
            throw new IllegalArgumentException("property serviceInterface is required.");
        }
		ProxyFactory pf = new ProxyFactory(getServiceInterface(), this);//用当前对象包装接口
        this.serviceProxy = pf.getProxy(getBeanClassLoader());
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
	        Object client = clientConstructor.newInstance(protocolFactory.getProtocol(getTransport()));
	        ClassLoader originalClassLoader = overrideThreadContextClassLoader();
	        try {
	            return method.invoke(client, args);
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        } catch (Throwable ex) {
	            throw convertException(ex);
	        } finally {
	            resetThreadContextClassLoader(originalClassLoader);
	        }
	}
	
}
