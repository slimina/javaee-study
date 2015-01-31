package cn.slimsmart.spring.demo.Interface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//实现该接口,在ApplicationContext运行的时候被通知并注入ApplicationContext
@Component
public class ApplicationContextAwareTest implements ApplicationContextAware {
	
	private Log log = LogFactory.getLog(getClass());

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		log.info("-----------ApplicationContext is runing");
	}
}
