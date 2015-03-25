package cn.slimsmart.memcache.demo.test.spring;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughMultiCache;
import com.google.code.ssm.api.ReadThroughMultiCacheOption;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;

/**
 * 参考：http://www.tuicool.com/articles/2ey26nq
 * http://wenku.baidu.com/view/fb35e3b8aeaad1f347933f39.html
 */
@Repository
public class StudentDao {
	
	private static final String NAMESPACE="student";
	
	/**
	 * @ReadThroughSingleCache 作用：读取Cache中数据，如果不存在，则将读取的数据存入Cache
	 * @ReadThroughMultiCache  返回值 获得参数 和 保存一样，都只能使用List 这里比较纠结
	 * @ReadThroughAssignCache 指定assignedKey，无参数方法
	 * key生成规则：ParameterValueKeyProvider指定的参数，如果该参数对象中包含CacheKeyMethod注解的方法，则调用其方法，否则调用toString方法
	 */
	@ReadThroughSingleCache(namespace=NAMESPACE,expiration=3600)
	public Student get(@ParameterValueKeyProvider String key){
		System.out.println("缓存没有命中");
		Student s = new Student();
		s.setId("db_10001");
		s.setAge(22);
		s.setName("db_data");
		s.setBirthday(new Date());
		return s;
	}
	
	@ReadThroughMultiCache(namespace=NAMESPACE,expiration=3600,option=@ReadThroughMultiCacheOption(addNullsToCache=true,generateKeysFromResult=true,skipNullsInResult=false))
	public List<Student> getList(@ParameterValueKeyProvider List<String> idList){
		Student s = new Student();
		s.setId("db_10001");
		s.setAge(22);
		s.setName("db_data");
		s.setBirthday(new Date());
		return Arrays.asList(s);
	}
	
	
	/**
	 * @InvalidateSingleCache, @InvalidateMultiCache, @InvalidateAssignCache
	 * 作用：失效Cache中的数据
	 * key生成规则：使用 ParameterValueKeyProvider注解时，与ReadThroughSingleCache一致
	 * 使用 ReturnValueKeyProvider 注解时，key为返回的对象的CacheKeyMethod或toString方法生成
	 */
	@InvalidateSingleCache(namespace=NAMESPACE)
	public void deleteStudent(@ParameterValueKeyProvider Student student){
		//TODO: delete db data;
	}
	
	/**
	 * @UpdateSingleCache, @UpdateMultiCache, @UpdateAssignCache
	 * 作用：更新Cache中的数据
	 * key生成规则：ParameterValueKeyProvider指定
	 * @ReturnDataUpdateContent：方法调用后生成的数据，作为更新缓存的数据
	 * @ParameterDataUpdateContent 这个参数注解，表示更新之后，更新的值就是 int  id ,很明显，这里参数最好是user 对象
	 * 注：上述两个注解，必须与Update*系列的注解一起使用
	 */
	@UpdateSingleCache(namespace=NAMESPACE,expiration=3600)
	public void updateStudent(@ParameterValueKeyProvider Student student){
		//TODO: update db data;
	}
	

}
