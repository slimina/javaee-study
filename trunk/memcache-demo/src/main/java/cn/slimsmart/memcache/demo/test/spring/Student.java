package cn.slimsmart.memcache.demo.test.spring;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * memcached相当于一个功能强大的Map，通过Key/Value的形式来缓存POJO实体，在定义实体的时候，
 * 可通过@CacheKeyMethod标签来为实体指定Key值，同时实体及实体的每个成员变量必须是可序列化的，
 * 可实现Serializable接口，或通过Externalizable接口来为实体指定序列化方法。
 */
public class Student implements Externalizable {

	private static final long serialVersionUID = 1L;
	private static final int CLASS_VERSION = 1;

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
	public void writeExternal(ObjectOutput out) throws IOException {
		out.write(CLASS_VERSION);
		out.writeUTF(id);
		out.writeUTF(name);
		out.writeInt(age);
		out.writeObject(birthday);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		in.readInt(); // reads CLASS_VERSION
		id=in.readUTF();
		name=in.readUTF();
		age=in.readInt();
		birthday=(Date)in.readObject();
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
		return String.format("Student[id=%s,name=%s,age=%d,birthday={0:G}]", id, name, age, birthday);
	}

	//注释：@CacheKeyMethod，这个是用来标识memcached 进行缓存操作时获取key的方法。
	@CacheKeyMethod
	public String cacheKey() {
		return "student_"+this.id;
	}
}
