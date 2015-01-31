package cn.slimsmart.spring.demo.Interface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

//BeanPostProcessor是BeanFactory的钩子允许用户对新建的Bean进行修改
@Component
public class BeanPostProcessorTest implements BeanPostProcessor {
	
	private Log log = LogFactory.getLog(getClass());

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.info("--------初始化之后调用：bean="+bean+",beanName="+beanName);
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.info("--------初始化之前调用：bean="+bean+",beanName="+beanName);
		return bean;
	}

}
