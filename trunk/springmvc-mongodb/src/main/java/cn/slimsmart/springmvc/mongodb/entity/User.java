package cn.slimsmart.springmvc.mongodb.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	private String id;
	private String username;
	private int age;
	private Date createTime;

	public User() {
	}

	public User(String username, int age, Date createTime) {
		super();
		this.username = username;
		this.age = age;
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
