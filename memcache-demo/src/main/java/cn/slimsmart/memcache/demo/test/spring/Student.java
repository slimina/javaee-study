package cn.slimsmart.memcache.demo.test.spring;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.code.ssm.api.CacheKeyMethod;

/**
 * memcached相当于一个功能强大的Map，通过Key/Value的形式来缓存POJO实体，在定义实体的时候，
 * 可通过@CacheKeyMethod标签来为实体指定Key值，同时实体及实体的每个成员变量必须是可序列化的，
 * 可实现Serializable接口，或通过Externalizable接口来为实体指定序列化方法。
 */
@JsonIgnoreProperties
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private int age;
	private Date birthday;

	//注释：@CacheKeyMethod，这个是用来标识memcached 进行缓存操作时获取key的方法。
	@CacheKeyMethod
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (!id.equals(other.id))
			return false;
		if (!name.equals(other.name))
			return false;
		if (age != other.age)
			return false;
		if (birthday.compareTo(other.birthday) != 0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Student[id=%s,name=%s,age=%d,birthday=%s]", id, name, age, birthday);
	}
}
