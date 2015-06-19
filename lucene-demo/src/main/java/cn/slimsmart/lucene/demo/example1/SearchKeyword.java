package cn.slimsmart.lucene.demo.example1;

import java.io.File;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchKeyword {

	public static void search(String queryString) throws Exception {
		String[] fields = { "id", "name", "desc" };
		String indexDir = "src/main/resources";// 索引目录
		Analyzer analyzer = new IKAnalyzer(true);// 采用的分词器

		QueryParser queryParse = new MultiFieldQueryParser(fields, analyzer);
		queryParse.setPhraseSlop(3);
		Query query = queryParse.parse(queryString);
		Directory directory = FSDirectory.open(new File(indexDir));
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(directoryReader);
		/**
		 * 排序
		 * Sort sort=new Sort(new SortField("birthdays",Type.STRING,false));
		 * TopDocs topDocs = isearcher.search(query, filter, topnum, sort)
		 * 
		 * 分页：
		 * TopFieldCollector c = TopFieldCollector.create(sort, first+end, false, false, false, false);
		 * isearcher.search(query, c);
		 * ScoreDoc[] hits = c.topDocs(first, end).scoreDocs;
		 */
				
		TopDocs topDocs = isearcher.search(query, null, 1000);
		
		// 高亮设置
		Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		/*
		 * Term term = new Term(USERNAME, content); query = new TermQuery(term);
		 */
		Scorer scorer = new QueryScorer(query);
		Highlighter highlighter = new Highlighter(formatter, scorer);
		Fragmenter fragmenter = new SimpleFragmenter(100);// 设置每次返回的字符数
		highlighter.setTextFragmenter(fragmenter);

		System.out.println("总共有[" + topDocs.totalHits + "]条匹配结果");
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			System.out.println("-------------------------");
			Document doc = isearcher.doc(scoreDoc.doc);
			System.out.println(doc.get("desc"));
			TokenStream tokenStream = analyzer.tokenStream("desc",new StringReader(doc.get("desc"))); 
			String str = highlighter.getBestFragment(tokenStream, doc.get("desc"));   
			System.out.println(str);
			String desc = highlighter.getBestFragment(analyzer,"desc", doc.get("desc"));
			System.out.println(desc);
			System.out.println("-------------------------");
		}
		directoryReader.close();
		directory.close();
	}

}
