package cn.slimsmart.unit.test.demo.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNGTest {
	
	@Test
	public void testEquals() {
		String str = "abc";
		Assert.assertEquals("abc", str);
	}
	
	@Test
	public void testAdd() {
		int a = 1+3;
		Assert.assertTrue(a==4);
	}
}
