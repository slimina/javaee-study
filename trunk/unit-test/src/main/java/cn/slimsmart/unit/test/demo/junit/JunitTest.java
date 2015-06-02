package cn.slimsmart.unit.test.demo.junit;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit测试实例
 * @author slimina
 *
 */
public class JunitTest {

	private AddService addService;

	@BeforeClass //全局只会执行一次，而且是第一个运行
	public static void beforeClass() {
		System.out.println("beforeClass invoked.");
	}

	@Before  //在测试方法运行之前运行
	public void setUp() {
		System.out.println("初始化");
		addService = new AddServiceImpl();
	}

	/**
	 *  测试方法
	 * @Test注解提供2个参数
	 * “expected”，定义测试方法应该抛出的异常，如果测试方法没有抛出异常或者抛出了一个不同的异常，测试失败
	 * “timeout”，如果测试运行时间长于该定义时间，测试失败（单位为毫秒）
	 */
	@Test
	public void testAdd() {
		assertTrue(addService.add(1, 1) == 2);
	}
	
	@Test
	public void testAdd1() {
		assertSame(addService.add(1, 2) , 3);
	}

	@After //在测试方法运行之后允许
	public void tearDown() {
		addService = null;
		System.out.println("测试退出");
	}

	@AfterClass //全局只会执行一次，而且是最后一个运行
	public static void afterClass() {
		System.out.println("afterClass invoked.");
	}
}
