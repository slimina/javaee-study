package cn.slimsmart.disruptor.demo.test.demo4;


import com.lmax.disruptor.EventHandler;

//通过实现接口 com.lmax.disruptor.EventHandler<T> 定义事件处理的具体实现
public class LogEventHandler implements EventHandler<LogEvent> {

    @Override
    public void onEvent(LogEvent event, long sequence, boolean endOfBatch) throws Exception {
             System.out.println("----------------");
             System.out.println("event:"+event.getMessage());
             System.out.println("sequence : "+sequence);
             System.out.println("endOfBatch: "+ endOfBatch);
    }
}
