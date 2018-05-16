package cn.slimsmart.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 参考实例
 * https://github.com/Netflix/Hystrix/tree/master/hystrix-examples
 */
public class HystrixHelloWorld extends HystrixCommand<String> {

    private final String name;

    public HystrixHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }
    protected String run() throws Exception {
        return "Hello " + name + "!";
    }
}
