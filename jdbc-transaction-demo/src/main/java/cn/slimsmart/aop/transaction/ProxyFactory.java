package cn.slimsmart.aop.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import javax.sql.DataSource;

import cn.slimsmart.aop.transaction.annotation.Transactional;

//代理
public class ProxyFactory implements InvocationHandler {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	private Object source;
	
	public ProxyFactory(Object source){
		this.source = source;
	}
	public ProxyFactory(){
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Exception{
		Method methodImpl = source.getClass().getMethod(method.getName(), method.getParameterTypes());
    	if(methodImpl!=null && methodImpl.isAnnotationPresent(Transactional.class)){
    		Connection conn = dataSource.getConnection();
    		TransactionManager.set(conn, dataSource);
    		//开启事务
    		conn.setAutoCommit(false);
    		Object obj = method.invoke(source, args);
    		conn.commit();
    		TransactionManager.remove();
    		((MyDataSource)dataSource).freeConnection(conn);
    		return obj;
    	}else{
    		return method.invoke(source, args);
    	}
    }    
      
    public static <T> T newMapperProxy(Class<T> mapperInterface,InvocationHandler handler) {  
        ClassLoader classLoader = mapperInterface.getClassLoader();  
        Class<?>[] interfaces = new Class[]{mapperInterface};  
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);  
      }  
}  
