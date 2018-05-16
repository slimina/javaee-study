package cn.slimsmart.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by zhutw on 2018/5/15.
 */
public class CommandHelloFailure extends HystrixCommand<String> {

    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
            throw new RuntimeException("this command always fails");
    }

    @Override
    protected String getFallback() { //降级
        return "Hello Failure " + name + "!";
    }

}
