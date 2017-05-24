package cn.slimsmart.spring.demo.event;

import org.springframework.context.ApplicationEvent;

//B事件
public class BEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	private String name = "BBB";
	public String getName() {
		return name;
	}
	
	public BEvent(Object source) {
		super(source);
	}
	
}
