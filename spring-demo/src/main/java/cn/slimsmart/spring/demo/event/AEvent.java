package cn.slimsmart.spring.demo.event;

import org.springframework.context.ApplicationEvent;

//A事件
public class AEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	private String name = "AAAA";
	public String getName() {
		return name;
	}
	
	public AEvent(Object source) {
		super(source);
	}
}
