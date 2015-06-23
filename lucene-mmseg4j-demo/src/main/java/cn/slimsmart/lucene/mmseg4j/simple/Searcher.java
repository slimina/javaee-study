package cn.slimsmart.lucene.mmseg4j.simple;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

public class Searcher {
	
	 /** 
     * 搜索器，根据输入的文本去搜索 
     * @param words     输入的文本 
     * @param indexPath     索引目录
     * @throws CorruptIndexException 
     * @throws IOException 
     * @throws ParseException 
     */  
    public static void searcher(String words,String indexPath) throws CorruptIndexException,  
            IOException, ParseException {  
        File indexDir = new File(indexPath);  
        //索引目录  
        Directory dir=FSDirectory.open(indexDir.toPath()); 
        //根据索引目录创建读索引对象  
        IndexReader reader = DirectoryReader.open(dir);  
        //搜索对象创建  
        IndexSearcher searcher = new IndexSearcher(reader);  
        //中文分词  
        Analyzer analyzer = new ComplexAnalyzer();
        //创建查询解析对象  
        QueryParser parser = new MultiFieldQueryParser(new String[]{"title","content"}, analyzer);  
        parser.setDefaultOperator(QueryParser.AND_OPERATOR);  
        //根据域和目标搜索文本创建查询器  
        Query query = parser.parse(words);  
        System.out.println("Searching for: " + query.toString(words));  
        //对结果进行相似度打分排序  
        TopScoreDocCollector collector = TopScoreDocCollector.create(5 * 10);  
        searcher.search(query, collector);  
        //获取结果  
        ScoreDoc[] hits = collector.topDocs().scoreDocs;  
        int numTotalHits = collector.getTotalHits();  
        System.out.println("一共匹配"+numTotalHits+"个网页");  
        //显示搜索结果  
        for (int i = 0; i < hits.length; i++) {  
            Document doc = searcher.doc(hits[i].doc);  
            String url = doc.get("url");  
            String title=doc.get("title");  
            String content=doc.get("content");  
            System.out.println((i + 1) + "." + title);  
            System.out.println("-----------------------------------");  
            System.out.println(content.substring(0,100)+"......");  
            System.out.println("-----------------------------------");  
            System.out.println(url);  
            System.out.println();  
        }  
    } 
}
