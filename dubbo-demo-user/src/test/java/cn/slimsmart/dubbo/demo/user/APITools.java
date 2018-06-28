package cn.slimsmart.dubbo.demo.user;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class APITools {

    private static final String DUBBO_PRE= "$dubbo_";

    private final ClassPathXmlApplicationContext mContext;

    public APITools(String... xmls) {
        mContext = new ClassPathXmlApplicationContext(xmls);
    }

    public void startService() {
        mContext.start();
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopService() {
        synchronized (this) {
            this.notifyAll();
            mContext.stop();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String spKey) {

        T instance = null;
        try {
            instance = (T) mContext.getBean(spKey);
        } catch (Exception expose) {
            instance = null;
        }
        if (null == instance) {
            // 需要在spring consumer.xml配置dubbo bean
            // bean的名称以"$dubbo_"开头
            instance = (T) mContext.getBean(DUBBO_PRE + spKey);
        }
        return instance;
    }
}
