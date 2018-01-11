package cn.slimsmart.java8.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Java 8打破了这条规则，引入了重复注解机制，这样相同的注解可以在同一地方声明多次。
//重复注解机制本身必须用@Repeatable注解
//这并不是语言层面上的改变，更多的是编译器的技巧，底层的原理保持不变
//反射相关的API提供了新的函数getAnnotationsByType()来返回重复注解的类型
public class RepeatableTest {

	@Target(ElementType.TYPE) //指定注解位置
	@Retention( RetentionPolicy.RUNTIME ) //指定注解生效策略
	@interface Filters{
		Filter[] value();
	}
	
	@Target(ElementType.TYPE)
	@Repeatable(Filters.class) //用于指示其声明的注释类型  Filter和Filters等效 ，Java编译器并不想让程序员意识到Filters的存在。
	@Retention(RetentionPolicy.RUNTIME)
	@interface Filter{
		String value();
	}
	
	@Filter( "filter1" )
    @Filter( "filter2" )
    public interface Filterable {        
    }
	
	public static void main(String[] args) {
		for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) {
            System.out.println( filter.value() );
        }
	}
}
