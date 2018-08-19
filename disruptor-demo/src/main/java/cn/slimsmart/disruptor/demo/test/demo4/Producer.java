package cn.slimsmart.disruptor.demo.test.demo4;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;


//Disruptor 还提供另外一种形式的调用来简化以上操作，并确保 publish 总是得到调用。
//此外，Disruptor 要求 RingBuffer.publish 必须得到调用的潜台词就是，如果发生异常也一样要调用 publish ，
// 那么，很显然这个时候需要调用者在事件处理的实现上来判断事件携带的数据是否是正确的或者完整的，这是实现者应该要注意的事情。
public class Producer {

    private Disruptor<LogEvent> disruptor;
    public Producer(Disruptor<LogEvent> disruptor){
        this.disruptor = disruptor;
    }

    public void publishEvent(long data){
        RingBuffer<LogEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(translator,data);
    }

    public static Translator translator = new Translator();

    static class Translator implements EventTranslatorOneArg<LogEvent,Long>{
        @Override
        public void translateTo(LogEvent event, long sequence, Long data) {
            event.setMessage("message ： " + data);
        }
    }
}


