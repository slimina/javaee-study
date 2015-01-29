package cn.slimsmart.java.demo.valid;

import javax.validation.constraints.NotNull;

public class Course {
	
	@NotNull(message="课程名不能为空")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}