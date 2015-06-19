package cn.slimsmart.lucene.demo.example1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class CreateIndex {

	public static void write() throws Exception {
		String indexDir = "src/main/resources";// 索引目录
		// 内存
		// RAMDirectory = new RAMDirectory();
		Directory fsDirectory = FSDirectory.open(new File(indexDir));
		IndexWriter fsIndexWriter = new IndexWriter(fsDirectory, getConfig());
		fsIndexWriter.addDocuments(getData());
		// 通过内存索引写入到文件中
		// fsIndexWriter.addIndexes(new Directory[] { RAMDirectory });
		fsIndexWriter.commit();
		fsIndexWriter.close();
		fsDirectory.close();
	}

	public static IndexWriterConfig getConfig() {
		Analyzer analyzer = new IKAnalyzer(true);// 采用的分词器
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		return config;
	}

	public static Iterable<? extends Iterable<? extends IndexableField>> getData() {
		List<Document> List = new ArrayList<Document>();
		List<People> datas = People.getInitList();
		for (People p : datas) {
			Document doc = new Document();
			// StoredField 仅仅存储，没有索引的
			// intField LongField 这样字段用于排序和过滤
			doc.add(new StringField("id", p.getId(), Field.Store.YES));
			doc.add(new StringField("name", p.getName(), Field.Store.YES));
			doc.add(new TextField("desc", p.getDesc(), Field.Store.YES));
			List.add(doc);
		}
		return List;
	}

	public void deleteDoc(String id) {
		try {
			Analyzer analyzer = new IKAnalyzer(true);// 采用的分词器
			String indexDir = "src/main/resources";// 索引目录
			Directory dir = FSDirectory.open(new File(indexDir));
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(dir, iwc);
			writer.deleteDocuments(new Term("id", id));
			//更新索引
			//writer.updateDocument(term, doc);
			writer.commit();
			writer.close();
			dir.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
