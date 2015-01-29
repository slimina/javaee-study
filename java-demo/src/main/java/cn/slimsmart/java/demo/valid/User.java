package cn.slimsmart.java.demo.valid;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class User {
	@NotNull(message="姓名不能为空")
	@Size(min=1,max=4,message="姓名长度介于1和4个字符")
	private String name;
	@NotNull(message="年龄不能为空")
	@Min(value=18,message="年龄不能小于18")
	@Max(value=70,message="年龄不能大于70")
	private int age;
	@Email(message="邮箱地址格式不正确")
	private String mail;
	@Pattern(regexp="[男女]",message="性别只能为男或女")
	private String sex;

	@Valid //标记course也做校验
	private Course course;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
