package cn.slimsmart.hystrix.demo;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by zhutw on 2018/5/15.
 */
public class HystrixHelloWorldTest {

    @Test
    public void testSynchronous(){
        assertEquals("Hello World!", new HystrixHelloWorld("World").execute());
        assertEquals("Hello Bob!", new HystrixHelloWorld("Bob").execute());
    }

    @Test
    public void testAsynchronous1() throws Exception { //测试异步
        assertEquals("Hello World!", new HystrixHelloWorld("World").queue().get());
        assertEquals("Hello Bob!", new HystrixHelloWorld("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws Exception { //测试异步
        Future<String> fWorld = new HystrixHelloWorld("World").queue();
        Future<String> fBob = new HystrixHelloWorld("Bob").queue();
        assertEquals("Hello World!", fWorld.get());
        assertEquals("Hello Bob!", fBob.get());
    }

    @Test
    public void testObservable() throws Exception {
        Observable<String> fWorld = new HystrixHelloWorld("World").observe();
        Observable<String> fBob = new HystrixHelloWorld("Bob").observe();
        // blocking
        assertEquals("Hello World!", fWorld.toBlocking().single());
        assertEquals("Hello Bob!", fBob.toBlocking().single());
        fWorld.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                // 这里可以什么都不做
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }
        });
        fBob.subscribe(new Action1<String>() {
            @Override
            public void call(String v) {
                System.out.println("onNext: " + v);
            }
        });
    }
}
