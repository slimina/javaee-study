package cn.slimsmart.lucene.demo.example.demo;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class MySearch {

	public static void main(String[] args) {
		Analyzer analyzer = new IKAnalyzer();
        File indexDir = new File("src/main/resources");
        try {
            Directory fsDirectory = FSDirectory.open(indexDir);
            DirectoryReader ireader = DirectoryReader.open(fsDirectory);
            IndexSearcher isearcher = new IndexSearcher(ireader);
            QueryParser qp = new QueryParser("content", analyzer);         //使用QueryParser查询分析器构造Query对象
            qp.setDefaultOperator(QueryParser.AND_OPERATOR);
            Query query = qp.parse("Lucene");     // 搜索Lucene
            TopDocs topDocs = isearcher.search(query , 5);      //搜索相似度最高的5条记录
            System.out.println("命中:" + topDocs.totalHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < topDocs.totalHits; i++){
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                System.out.println("内容:" + targetDoc.toString());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

}
