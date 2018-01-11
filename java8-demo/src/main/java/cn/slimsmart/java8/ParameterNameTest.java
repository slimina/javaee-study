package cn.slimsmart.java8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

//方法参数的名字能保留在Java字节码中
//通过反射API与Parameter.getName()方法
//通过maven-compiler-plugin的配置可以将-parameters参数添加到编译器中去。
public class ParameterNameTest {
	
	 public static void main(String[] args) throws Exception {
	        Method method = ParameterNameTest.class.getMethod( "main", String[].class );
	        for( final Parameter parameter: method.getParameters() ) {
	            System.out.println( "Parameter: " + parameter.getName() );
	        }
	    }
}
