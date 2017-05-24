package cn.slimsmart.spring.demo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//监听A事件
@Component
public class AEventListener implements ApplicationListener<AEvent>{
	
	@Async
	public void onApplicationEvent(AEvent aEvent) {
		System.out.println(this.getClass().getName());
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println("event name : "+aEvent.getName());
		System.out.println(aEvent.getSource());
	}
}
