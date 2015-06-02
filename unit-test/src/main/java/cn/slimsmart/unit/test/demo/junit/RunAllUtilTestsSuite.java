package cn.slimsmart.unit.test.demo.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * 测试套件类, 此类中可注解全部的测试组套件或测试类
 * 注意:要保证测试套件之间没有循环包含关系，否则无尽的循环就会出现在您的面前
 */

@RunWith(Suite.class) 
@Suite.SuiteClasses({
	JunitTest.class
	//可以添加多个单元测试，同一执行，以逗号分隔
	})
public class RunAllUtilTestsSuite{
	
}
