package cn.slimsmart.spring.demo.Interface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//如果期望在BeanFactory 设置所有的属性后作出进一步的反应可以实现该接口
@Component
public class InitializingBeanTest  implements InitializingBean, BeanNameAware {
	
	private Log log = LogFactory.getLog(getClass());
	
	private String beanName;

	public void setBeanName(String beanName) {
		log.info("--------setBeanName:"+beanName);
		this.beanName = beanName;
	}

	public void afterPropertiesSet() throws Exception {
		log.info("--------Bean的属性都被设置完成:"+beanName);
	}
}
