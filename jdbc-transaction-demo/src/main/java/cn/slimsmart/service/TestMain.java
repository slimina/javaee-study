package cn.slimsmart.service;

import cn.slimsmart.aop.transaction.FacadeProxy;
import cn.slimsmart.aop.transaction.JdbcTemplate;
import cn.slimsmart.aop.transaction.MyDataSource;

//测试
public class TestMain {
	
	public static void main(String[] args) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.instance());
		FacadeProxy proxy = new FacadeProxy(new UserServiceImpl(jdbcTemplate));
		proxy.setDataSource(MyDataSource.instance());
		UserService userServiceProxy = proxy.newMapperProxy(UserService.class,proxy);
		System.out.println(userServiceProxy.save("1111", 20));
	}
}
