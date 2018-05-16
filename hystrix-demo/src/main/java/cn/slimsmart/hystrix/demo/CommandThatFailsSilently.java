package cn.slimsmart.hystrix.demo;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhutw on 2018/5/16.
 */
public class CommandThatFailsSilently extends HystrixCommand<List<String>> {

    private final boolean throwException;

    public CommandThatFailsSilently(boolean throwException) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.throwException = throwException;
    }

    @Override
    protected List<String> run() {
        if (throwException) {
            throw new RuntimeException("failure from CommandThatFailsFast");
        } else {
            ArrayList<String> values = new ArrayList<String>();
            values.add("success");
            return values;
        }
    }

    @Override
    protected List<String> getFallback() {
        return Collections.emptyList();
    }
}
