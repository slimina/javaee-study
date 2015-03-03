package cn.slimsmart.avro.demo;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class Test {

	public static void main(String[] args) {
		// 3种生成user对象的方法
		User user1 = new User();
		user1.setName("张山");
		user1.setAge(23);
		user1.setPhone("123456789");

		User user2 = new User("李斯", 45, "987654321");

		User user3 = User.newBuilder().setName("王二").setAge(57).setPhone("456893256").build();

		// 序列化user到文件中
		File file = new File("users.avro");
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		try {
			dataFileWriter.create(user1.getSchema(), new File("src/main/avro/users.avro"));
			dataFileWriter.append(user1);
			dataFileWriter.append(user2);
			dataFileWriter.append(user3);
			dataFileWriter.flush();
			dataFileWriter.close();
		} catch (IOException e) {
		}

		// 从文件中反序列化对象
		DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
		DataFileReader<User> dataFileReader = null;
		try {
			dataFileReader = new DataFileReader<User>(file, userDatumReader);
		} catch (IOException e) {
		}
		User user = null;
		try {
			while (dataFileReader.hasNext()) {
				user = dataFileReader.next(user);
				System.out.println(user);
			}
		} catch (IOException e) {
		}
	}
}
