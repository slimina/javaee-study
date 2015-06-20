package cn.slimsmart.lucene.mmseg4j.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

public class Test {
	static Analyzer analyzer = null;
	static Directory directory = null;
	static String text = "CSDN.NET - 全球最大中文IT社区，为IT专业技术人员提供最全面的信息传播和服务平台";

	public static void main(String[] args) throws Exception {
		analyzer = new ComplexAnalyzer();
		directory = new RAMDirectory();
		write();
		search();
	}

	/**
	 * 简单分词
	 * 
	 * @throws Exception
	 */
	public static void write() throws Exception {
		IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
		iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter iwriter = new IndexWriter(directory, iwConfig);
		Document doc = new Document();
		doc.add(new TextField("text", text, Field.Store.YES));
		iwriter.addDocument(doc);
		iwriter.commit();
		iwriter.close();
	}

	public static void search() throws Exception {
		IndexReader ireader = DirectoryReader.open(directory);  
	    IndexSearcher searcher = new IndexSearcher(ireader);  
		QueryParser qp = new QueryParser("text", analyzer);
		Query q = qp.parse("全球最大");
		System.out.println(q);
		TopDocs tds = searcher.search(q, 10);
		System.out.println("======size:" + tds.totalHits + "========");
		for (ScoreDoc sd : tds.scoreDocs) {
			System.out.println(sd.score);
			System.out.println(searcher.doc(sd.doc).get("text"));
		}
	}
}
