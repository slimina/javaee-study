package cn.slimsmart.thrift.spring.http.remote;

//自定义异常
public class ThriftServletException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ThriftServletException(String message) {
		super(message);
	}

	public ThriftServletException(Throwable cause) {
		super(cause);
	}

	public ThriftServletException(String message, Throwable cause) {
		super(message, cause);
	}
}
