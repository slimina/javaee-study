package cn.slimsmart.solr.demo.solrj;

import org.apache.solr.client.solrj.beans.Field;

public class Student {
	
	@Field
	private String id;
	@Field
	private String name;
	@Field
	private int age;
	@Field
	private String desc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
