package cn.slimsmart.disruptor.demo.test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

public class Test3 {

	public static void main(String[] args) throws Exception {
		long beginTime=System.currentTimeMillis();  
        
        int bufferSize=1024;  
        ExecutorService executor=Executors.newFixedThreadPool(4);  
        //这个构造函数参数，相信你在了解上面2个demo之后就看下就明白了，不解释了~  
        Disruptor<TradeTransaction> disruptor=new Disruptor<TradeTransaction>(new EventFactory<TradeTransaction>() {  
            @Override  
            public TradeTransaction newInstance() {  
                return new TradeTransaction();  
            }  
        }, bufferSize, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());  
          
        //使用disruptor创建消费者组C1,C2  
        EventHandlerGroup<TradeTransaction> handlerGroup=disruptor.handleEventsWith(new TradeTransactionVasConsumer(),new TradeTransactionHandler());  
          
        TradeTransactionJMSNotifyHandler jmsConsumer=new TradeTransactionJMSNotifyHandler();  
        //声明在C1,C2完事之后执行JMS消息发送操作 也就是流程走到C3  
        handlerGroup.then(jmsConsumer);  
          
          
        disruptor.start();//启动  
        CountDownLatch latch=new CountDownLatch(1);  
        //生产者准备  
        executor.submit(new TradeTransactionPublisher(latch, disruptor));  
        latch.await();//等待生产者完事.  
        disruptor.shutdown();  
        executor.shutdown();  
          
        System.out.println("总耗时:"+(System.currentTimeMillis()-beginTime));  
	}
}

class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {  
    @Override  
    public void onEvent(TradeTransaction event, long sequence,  
            boolean endOfBatch) throws Exception {  
        //do send jms message  
    }  
}

class TradeTransactionPublisher implements Runnable{  
    Disruptor<TradeTransaction> disruptor;  
    private CountDownLatch latch;  
    private static int LOOP=10000000;//模拟一千万次交易的发生  
  
    public TradeTransactionPublisher(CountDownLatch latch,Disruptor<TradeTransaction> disruptor) {  
        this.disruptor=disruptor;  
        this.latch=latch;  
    }  
  
    @Override  
    public void run() {  
        TradeTransactionEventTranslator tradeTransloator=new TradeTransactionEventTranslator();  
        for(int i=0;i<LOOP;i++){  
            disruptor.publishEvent(tradeTransloator);  
        }  
        latch.countDown();  
    }  
}

class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction>{  
    private Random random=new Random();  
    @Override  
    public void translateTo(TradeTransaction event, long sequence) {  
        this.generateTradeTransaction(event);  
    }  
    private TradeTransaction generateTradeTransaction(TradeTransaction trade){  
        trade.setPrice(random.nextDouble()*9999);  
        return trade;  
    }  
}

class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {  
    @Override  
    public void onEvent(TradeTransaction event, long sequence,  
            boolean endOfBatch) throws Exception {  
        //do something....  
    }  
      
}  