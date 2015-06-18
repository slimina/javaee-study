package cn.slimsmart.lucene.demo.example.demo;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class MyIndex {
	 public static void main(String[] args) {
	        String ID;
	        String content;
	        ID = "1231";
	        content = "BuzzFeed has compiled an amazing array of " +
	                "ridiculously strange bridesmaid snapshots, courtesy of Awkward Family Photos. ";
	        indexPost(ID, content);
	        ID = "1234";
	        content = "Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包";
	        indexPost(ID, content);
	        ID = "1235";
	        content = "Lucene不是一个完整的全文索引应用，而是是一个用Java写的全文索引引擎工具包，它可以方便的嵌入到各种应用中实现";
	        indexPost(ID, content);
	    }
	 
	    public static void indexPost(String ID, String content) {
	        File indexDir = new File("src/main/resources");
	        Analyzer analyzer = new IKAnalyzer();
	        //注意对于全文索引，不要使用StringField。 
	        TextField postIdField = new TextField("id", ID, Store.YES);
	        TextField postContentField = new TextField("content", content, Store.YES);
	        Document doc = new Document();
	        doc.add(postIdField);
	        doc.add(postContentField);
	        IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
	        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
	        try {
	            Directory fsDirectory = FSDirectory.open(indexDir);
	            IndexWriter indexWriter = new IndexWriter(fsDirectory, iwConfig);
	            indexWriter.addDocument(doc);
	            indexWriter.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
