package cn.slimsmart.disconf.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 演示分布式配置文件、分布式配置的更新Demo
 * 
 * @author zhutianwei
 * 
 */
@Service
public class DisconfDemoTask {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfDemoTask.class);

	@Autowired
	private SimpleRedisService simpleRedisService;

	@Autowired
	private JedisConfig jedisConfig;

	private static final String REDIS_KEY = "disconf_key";

	/**
	     * 
	     */
	public int run() {

	        try {

	            while (true) {

	                Thread.sleep(5000);

	                LOGGER.info("redis( " + jedisConfig.getHost() + ","
	                        + jedisConfig.getPort() + ")  get key: " + REDIS_KEY );                    

	            }

	        } catch (Exception e) {

	            LOGGER.error(e.toString(), e);
	        }

	        return 0;
	    }
}
