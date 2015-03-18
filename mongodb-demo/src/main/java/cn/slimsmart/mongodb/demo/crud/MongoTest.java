package cn.slimsmart.mongodb.demo.crud;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class MongoTest {

	private static MongoClient mongoClient;

	public static void main(String[] args) throws UnknownHostException {
		
		List<ServerAddress> list = new ArrayList<ServerAddress>();
		list.add(new ServerAddress("192.168.100.90", 30000));
		list.add(new ServerAddress("192.168.100.110", 30000));
		list.add(new ServerAddress("192.168.110.71", 30000));
		// 初始化mongoClient
		mongoClient = new MongoClient(list);
		// 1.数据库列表
		for (String s : mongoClient.getDatabaseNames()) {
			System.out.println("DatabaseName=" + s);
		}
		// 2.链接student数据库
		DB db = mongoClient.getDB("student");
		mongoClient.setWriteConcern(WriteConcern.JOURNALED);

		// 3.用户验证
		@SuppressWarnings("deprecation")
		boolean auth = db.authenticate("admin", "".toCharArray());
		System.out.println("auth=" + auth);

		// 4.集合列表
		Set<String> colls = db.getCollectionNames();
		for (String s : colls) {
			System.out.println("CollectionName=" + s);
		}

		// 5.获取摸个集合对象
		DBCollection coll = db.getCollection("user");
		System.out.println("----------save-------------");
		save(coll);
		System.out.println("----------query-------------");
		query(coll);
		System.out.println("----------update-------------");
		update(coll);
		System.out.println("----------delete-------------");
		delete(coll);
	}

	// 新增数据
	public static void save(DBCollection coll) {
		BasicDBObject doc = new BasicDBObject("_id", "8").append("name", new BasicDBObject("username", "zhangshan").append("nickname", "张珊"))
				.append("password", "123456").append("password", "123456").append("regionName", "北京").append("works", "5").append("birth", new Date());
		WriteResult result = coll.insert(doc);
		System.out.println("insert-result: " + result);
	}
	//查询
	public static void query(DBCollection coll) {
		// 1查询 - one
		DBObject myDoc = coll.findOne();
		System.out.println(myDoc);
		// 2 查询 - 数量
		System.out.println(coll.getCount());
		// 3查询 - 全部
		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			System.out.println("全部--------" + cursor.next());
		}
		// 4查询 - 过滤 - 等于
		BasicDBObject query = new BasicDBObject("age", 1);
		cursor = coll.find(query);
		while (cursor.hasNext()) {
			System.out.println("age=1--------" + cursor.next());
		}

		// 5查询 - 过滤条件 - 不等于
		query = new BasicDBObject("age", new BasicDBObject("$ne", 1));
		cursor = coll.find(query);
		while (cursor.hasNext()) {
			System.out.println("age!=1" + cursor.next());
		}

		// 6查询 - 过滤条件 - 20 < i <= 30
		query = new BasicDBObject("age", new BasicDBObject("$gt", 20).append("$lte", 30));
		cursor = coll.find(query);
		while (cursor.hasNext()) {
			System.out.println("20<age<=30" + cursor.next());
		}
	}
	
	//更新
	public static void update(DBCollection coll) {
		DBObject search = coll.findOne(new BasicDBObject("_id", "6"));
		BasicDBObject object = new BasicDBObject().append("$set", new BasicDBObject("password", "1211111")).append("$set",
				new BasicDBObject("birth", new Date()));
		/*
		 * upsert when true, inserts a document if no document matches the update query criteria
		 * multi when true, updates all documents in the collection that match the update query criteria, otherwise only updates one
		 */
		WriteResult result = coll.update(search, object, true, true);
		System.out.println("update-result: " + result);
	}

	//删除
	public static void delete(DBCollection coll) {
		DBObject search = coll.findOne(new BasicDBObject("_id", "6"));  
		WriteResult result = coll.remove(search);  
		System.out.println("remove-result: " + result);  
	}
}
