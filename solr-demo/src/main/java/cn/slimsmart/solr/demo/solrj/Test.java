package cn.slimsmart.solr.demo.solrj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

// 采用SolrInputDocument对象增加、删除索引
public class Test {

	static String URL = "http://192.168.18.119:9080/solr/test";

	public static void main(String[] args) {
		addDocs();
		//delDocs();
	}

	public static void addDocs() {
		long start = System.currentTimeMillis();
		Collection<Student> docs = new ArrayList<Student>();
		for (int i = 1; i < 50; i++) {
			Student s = new Student();
			//各属性的名称在conf/schema.xml中存在
			s.setId("efgh00"+i);
			s.setName("li si" + i);
			s.setAge(3 * i);
			s.setDesc(i+"今天天气很好");
			docs.add(s);
		}
		try {
			HttpSolrClient client = new HttpSolrClient(URL);
			client.addBeans(docs);
			client.optimize();
			client.commit();
			client.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("cost time:" + (System.currentTimeMillis() - start));
	}

	public static void delDocs() {
		long start = System.currentTimeMillis();
		try {
			HttpSolrClient client = new HttpSolrClient(URL);
			List<String> ids = new ArrayList<String>();
			for (int i = 1; i < 10; i++) {
				ids.add("efgh00" + i);
			}
			client.deleteById(ids);
			client.commit();
			client.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("cost time:" + (System.currentTimeMillis() - start));
	}
}
