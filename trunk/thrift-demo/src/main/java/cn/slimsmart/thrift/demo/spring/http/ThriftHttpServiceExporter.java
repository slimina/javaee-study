package cn.slimsmart.thrift.demo.spring.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 
 主要使用了spring的HttpRequestHandler接口和RemoteExporter接口。HttpRequestHandler接口用于暴露http服务
，这样就可以接受http的请求，这个如果使用servlet也是可以的。RemoteExporter这块其实主要就是使用了它getServiceInterface
和getProxyForService两个方法，这个和thrift服务端代码结合暴露内部的服务。
 * */
public class ThriftHttpServiceExporter extends ThriftExporter implements HttpRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!"POST".equals(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[] { "POST" }, "ThriftServiceExporter only supports POST requests");
		}
		ServletInputStream in = request.getInputStream();
		ServletOutputStream out = response.getOutputStream();
		try {
			response.setContentType(CONTENT_TYPE_THRIFT);
			invoke(in, out);
		} catch (Exception e) {
			response.setContentType("text/plain; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace(new PrintWriter(out, true));
			if (logger.isErrorEnabled()) {
				logger.error("Thrift server direct error", e);
			}
		}

	}
}
