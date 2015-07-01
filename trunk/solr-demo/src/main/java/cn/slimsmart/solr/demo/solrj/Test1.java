package cn.slimsmart.solr.demo.solrj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

// 采用POJOs增加、删除索引
public class Test1 {

	static String URL = "http://192.168.18.119:9080/solr/test";

	public static void main(String[] args) {
		addDocs();
		delDocs();
	}

	public static void addDocs() {
		long start = System.currentTimeMillis();
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (int i = 1; i < 50; i++) {
			SolrInputDocument doc1 = new SolrInputDocument();
			//各属性的名称在conf/schema.xml中存在
			doc1.addField("id", "abcd00" + i, 1.0f);
			doc1.addField("name", "zhan shan" + i, 1.0f);
			doc1.addField("age", 2 * i);
			doc1.addField("desc", "Apache Devicemap is a data repository containing devices attributes " + i);
			docs.add(doc1);
		}
		try {
			HttpSolrClient client = new HttpSolrClient(URL);
			client.add(docs.iterator());
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
				ids.add("abcd00" + i);
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
