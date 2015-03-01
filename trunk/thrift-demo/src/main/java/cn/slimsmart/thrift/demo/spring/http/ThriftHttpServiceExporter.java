package cn.slimsmart.thrift.demo.spring.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.HttpRequestHandler;

/**
<!-- Thrift export -->
<bean name="/paymentService" class="framework.support.thrift.ThriftHttpServiceExporter">
    <property name="service">
        <bean class="payment.service.impl.ThriftPaymentServiceImpl"/>
    </property>
    <property name="serviceInterface" value="api.payment.PaymentService$Iface"/>
</bean>
http://www.open-open.com/lib/view/open1357804231418.html
主要使用了spring的HttpRequestHandler接口和 RemoteExporter接口。HttpRequestHandler接口用于暴露http服务，这样就可以接受http的请求，这个如果使用servlet也是可以的。
RemoteExporter这块其实主要就是使用了它getServiceInterface和getProxyForService两个方法，这个和thrift服务端代码结合暴露内部的服务。
 * */
public class ThriftHttpServiceExporter extends ThriftExporter implements HttpRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!"POST".equals(request.getMethod()) && metadataXml != null) {// 非post则试图返回thrift生成的服务的metadata信息
            response.setContentType("text/xml; charset=UTF-8");
            FileCopyUtils.copy(metadataXml.openStream(), response.getOutputStream());
            return;
        }
        InputStream in = request.getInputStream();
        OutputStream out = response.getOutputStream();
        try {// 下面就thrift以二进制方式暴露服务
            ThriftContextHolder.init();
            response.setContentType("application/x-thrift");
            TTransport transport = new TIOStreamTransport(in, out);
             
            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory(true, true);//二进制方式 <span></span>             
            TProtocol protocol = protocolFactory.getProtocol(transport);
            // 因为使用的是spring的RemoteExport，所以getServiceInterface和getProxyForService方法会返回xml配置中的接口和接口实现类
            TProcessor processor = ThriftHelper.buildProcessor(getServiceInterface(), getProxyForService());   
            processor.invoker(protocol, protocol);  
        } catch (Throwable e) {
            response.setContentType("text/plain; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace(new PrintWriter(out, true));
            if (LOG.isErrorEnabled()) {
                LOG.error("Thrift server direct error", e);
            }
        } finally {
            ThriftContextHolder.reset();
        }
	}
}
