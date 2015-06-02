package cn.slimsmart.unit.test.demo.junit;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//用来说明此测试类的运行者，这里用了 SpringJUnit4ClassRunner
@RunWith(SpringJUnit4ClassRunner.class) 
//指定 Spring 配置信息的来源，支持指定 XML 文件位置或者 Spring 配置类名
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
//表明此测试类的事务启用，这样所有的测试方案都会自动的 rollback,
//@Transactional
//defaultRollback，是否回滚，默认为true
//transactionManager：指定事务管理器一般在spring配置文件里面配置
//@TransactionConfiguration(defaultRollback=true,transactionManager="transactionManager")
//但是需要兼容生产环境的配置和单元测试的配置，那么您可以使用 profile 的方式来定义 beans，
//@ActiveProfiles("test")
public class JunitSpringTest {
	
	@Autowired
	private AddService addService;
	
	@Before
	public void setUp(){
		System.out.println("初始化");
	}
	
	@Test
	public void testAdd() {
		assertTrue(addService.add(1, 1) == 2);
	}
	
	@After
	public void destroy() {
		System.out.println("退出，资源释放");
	}
}
