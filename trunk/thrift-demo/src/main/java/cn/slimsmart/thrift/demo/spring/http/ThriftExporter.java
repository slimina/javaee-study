package cn.slimsmart.thrift.demo.spring.http;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;

public class ThriftExporter extends RemoteExporter implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
        if (serviceRegistry != null) {
            List<MethodInterceptor> il = new ArrayList<MethodInterceptor>();
            il.add(new ClassLoaderInterceptor(getBeanClassLoader()));
            if (threadResourceManager != null) {
                il.add(new ThreadResourceSupportInterceptor(threadResourceManager));
            }
            serviceRegistry.exportService(getServiceInterface(), getProxyForService0(il));
        }
        super.setInterceptors(ArrayUtils.add(interceptors, new AnyExceptionConvertInterceptor()));
        this.processor = ThriftHelper.buildProcessor(getServiceInterface(), getProxyForService());
    }
}
