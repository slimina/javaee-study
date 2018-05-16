package cn.slimsmart.hystrix.demo;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by zhutw on 2018/5/16.
 */
public class CommandUsingRequestCacheInvalidationTest {

    @Test
    public void getGetSetGet() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            assertEquals("ValueBeforeSet_1", new CommandUsingRequestCacheInvalidation.GetterCommand(1).execute());
            CommandUsingRequestCacheInvalidation.GetterCommand commandAgainstCache = new CommandUsingRequestCacheInvalidation.GetterCommand(1);
            assertEquals("ValueBeforeSet_1", commandAgainstCache.execute());
            // confirm it executed against cache the second time
            assertTrue(commandAgainstCache.isResponseFromCache());
            // set the new value
            new CommandUsingRequestCacheInvalidation.SetterCommand(1, "ValueAfterSet_").execute();
            // fetch it again
            CommandUsingRequestCacheInvalidation.GetterCommand commandAfterSet = new CommandUsingRequestCacheInvalidation.GetterCommand(1);
            // the getter should return with the new prefix, not the value from cache
            assertFalse(commandAfterSet.isResponseFromCache());
            assertEquals("ValueAfterSet_1", commandAfterSet.execute());
        } finally {
            context.shutdown();
        }
    }
}
