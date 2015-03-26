package cn.slimsmart.memcache.demo.test.spring;

import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * 参考：http://tom-seed.iteye.com/blog/2104430
 */
@Repository
public class StudentCacheable {
	
	//applicationContext-cache-spring3.1.xml 配置的
	private static final String CACHENAME = "studentCache";

	/**
	 * @Cacheable 负责将方法的返回值加入到缓存中
	 *  注解可以用在方法或者类级别。当他应用于方法级别的时候，就是如上所说的缓存返回值了。当应用在类级别的时候，这个类的所有方法的返回值都将被缓存
	 *  value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
	 *  key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
	 *  condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL 如： condition = "#age < 25"
	 * 任何存储在缓存中的数据为了高速访问都需要一个key。Spring默认使用被@Cacheable注解的方法的签名来作为key，当然你可以重写key，自定义key可以使用SpEL表达式。
	 * 
	 * @CacheEvict：负责清除缓存
	 * value：缓存位置名称，不能为空，同上
	 * key：缓存的key，默认为空，同上
	 * condition：触发条件，只有满足条件的情况才会清除缓存，默认为空，支持SpEL
	 * allEntries：true表示清除value中的全部缓存，默认为false
	 * 
	 * @CachePut 注释，这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中，实现缓存与数据库的同步更新。
	 */
	
	// cache name: studentCache, expiration: 600s instead of default 300s
	@Cacheable(value ={CACHENAME+"#6000"},key="#id")
	public Student get(String id) {
		System.out.println("缓存没有命中->"+id);
		Student s = new Student();
		s.setId("user_10001");
		s.setAge(22);
		s.setName("db_data");
		s.setBirthday(new Date());
		return s;
	}
	
	//清除掉指定key的缓存  
	@CacheEvict(value=CACHENAME,key="#student.getId()")  
	public void delete(Student student) {  
	         System.out.println("delete cache :"+student.getId());  
	}  
	
	/**
	 * 一般来说，我们的更新操作只需要刷新缓存中某一个值，所以定义缓存的key值的方式就很重要，最好是能够唯一，因为这样可以准确的清除掉特定的缓存，而不会影响到其它缓存值 ，
	 * 比如我这里针对用户的操作，使用(userId+方法名称)的方式设定key值 ，当然，你也可以找到更适合自己的方式去设定。
	 * 关于SpEL的介绍，可以参考如下地址：http://static.springsource.org/spring/docs/3.1.0.M1/spring-framework-reference/html/expressions.html
	 */
}
