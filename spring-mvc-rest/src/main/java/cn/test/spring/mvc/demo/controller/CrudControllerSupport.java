package cn.test.spring.mvc.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制层基类
 * 
 * @author zhutianwei
 *
 * @param <T>
 */
public abstract class CrudControllerSupport {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
}
