package cn.slimsmart.disruptor.demo.test;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class TradeTransactionHandler implements EventHandler<TradeTransaction>,WorkHandler<TradeTransaction> {

	@Override
	public void onEvent(TradeTransaction event) throws Exception {
		//这里做具体的消费逻辑  
        event.setId(UUID.randomUUID().toString());//简单生成下ID  
        System.out.println(event.getId()+" : "+ event.getPrice());
	}

	@Override
	public void onEvent(TradeTransaction event, long sequence,
			boolean endOfBatch) throws Exception {
		this.onEvent(event);
	}  

}
