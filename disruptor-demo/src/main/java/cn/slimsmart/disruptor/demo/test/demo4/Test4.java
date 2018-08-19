package cn.slimsmart.disruptor.demo.test.demo4;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test4 {
  public static void main(String[] args) {
    //Disruptor 通过 java.util.concurrent.ExecutorService 提供的线程来触发 Consumer 的事件处理。
    //ExecutorService executorService = Executors.newCachedThreadPool();

    /*
    * Disruptor 定义了 com.lmax.disruptor.WaitStrategy 接口用于抽象 Consumer 如何等待新事件，这是策略模式的应用。
Disruptor 提供了多个 WaitStrategy 的实现，每种策略都具有不同性能和优缺点，根据实际运行环境的 CPU 的硬件特点选择恰当的策略，并配合特定的 JVM 的配置参数，能够实现不同的性能提升。
例如，BlockingWaitStrategy、SleepingWaitStrategy、YieldingWaitStrategy 等，其中，
BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现；
SleepingWaitStrategy 的性能表现跟 BlockingWaitStrategy 差不多，对 CPU 的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景；
YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性。

WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
    * */

    //启动 Disruptor
      EventFactory<LogEvent> eventFactory = new LogEventFactory();
      int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

      Disruptor<LogEvent> disruptor = new Disruptor<LogEvent>(eventFactory,
              ringBufferSize, Executors.defaultThreadFactory()
              , ProducerType.SINGLE,
              new YieldingWaitStrategy());
      EventHandler<LogEvent> eventHandler = new LogEventHandler();
      disruptor.handleEventsWith(eventHandler);
      disruptor.start();

      /*
      * Disruptor 的事件发布过程是一个两阶段提交的过程：
　　第一步：先从 RingBuffer 获取下一个可以写入的事件的序号；
　　第二步：获取对应的事件对象，将数据写入事件对象；
　　第三部：将事件提交到 RingBuffer;
事件只有在提交之后才会通知 EventProcessor 进行处理；
      * */
      RingBuffer<LogEvent> ringBuffer = disruptor.getRingBuffer();
      long sequence = ringBuffer.next();
      try{
          LogEvent event = ringBuffer.get(sequence);
          event.setMessage("message : " + System.nanoTime());
      }finally{
          ringBuffer.publish(sequence);
          //最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
          // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
      }
      //----
      ringBuffer = disruptor.getRingBuffer();
      sequence = ringBuffer.next();
      try{
          LogEvent event = ringBuffer.get(sequence);
          event.setMessage("message : " + System.nanoTime());
      }finally{
          ringBuffer.publish(sequence);
          //最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
          // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
      }


      new Producer(disruptor).publishEvent(System.nanoTime());
        disruptor.shutdown();

  }
}
