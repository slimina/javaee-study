package cn.slimsmart.spring.demo.Interface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

//如果Bean想知道在BeanFactory中设置的名字时可以实现该接口
@Component
public class BeanNameAwareTest implements BeanNameAware{
	
	private Log log = LogFactory.getLog(getClass());
	
	private String beanName;

	public void setBeanName(String beanName) {
		log.info("--------setBeanName:"+beanName);
		this.beanName = beanName;
	}
	
	public String getBeanName() {
        return beanName;
    }
}
