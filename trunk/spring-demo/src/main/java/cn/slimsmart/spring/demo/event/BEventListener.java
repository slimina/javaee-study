package cn.slimsmart.spring.demo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//监听B事件
@Component
public class BEventListener implements ApplicationListener<BEvent>{
	
	@Async
	public void onApplicationEvent(BEvent bEvent) {
		System.out.println(this.getClass().getName());
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("event name : "+bEvent.getName());
		System.out.println(bEvent.getSource());
	}
}
