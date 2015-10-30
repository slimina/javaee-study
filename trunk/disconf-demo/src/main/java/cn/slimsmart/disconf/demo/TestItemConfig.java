package cn.slimsmart.disconf.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfItem;


//测试配置项，非配置文件
//这里切面无法生效，因为SpringAOP不支持。
@Service
@Scope("singleton")
public class TestItemConfig{
	
	public static final String key = "user.name";
	
	
	private String name;


	@DisconfItem(key = key)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
