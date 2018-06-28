package cn.slimsmart.hystrix.demo;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by zhutw on 2018/5/16.
 */
public class CommandThatFailsSilentlyTest {

    @Test
    public void testSuccess() {
        assertEquals("success", new CommandThatFailsSilently(false).execute().get(0));
    }

    @Test
    public void testFailure() {
        try {
            assertEquals(0, new CommandThatFailsSilently(true).execute().size());
        } catch (HystrixRuntimeException e) {
            fail("we should not get an exception as we fail silently with a fallback");
        }
    }
}
