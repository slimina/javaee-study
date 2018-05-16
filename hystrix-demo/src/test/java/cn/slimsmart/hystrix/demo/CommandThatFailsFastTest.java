package cn.slimsmart.hystrix.demo;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by zhutw on 2018/5/15.
 */
public class CommandThatFailsFastTest {
    @Test
        public void testSuccess() {
            assertEquals("success", new CommandThatFailsFast(false).execute());
        }

        @Test
        public void testFailure() {
        try {
            new CommandThatFailsFast(true).execute();
            fail("we should have thrown an exception");
        } catch (HystrixRuntimeException e) {
            assertEquals("failure from CommandThatFailsFast", e.getCause().getMessage());
            e.printStackTrace();
        }
    }
}
