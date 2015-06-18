package cn.slimsmart.lucene.demo.example;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Test {

	public static void main(String[] args) throws Exception {
		createIndex();
		search();
	}

	public static void createIndex() throws Exception {
		// 索引文件的保存位置
		Directory dir = FSDirectory.open(new File("src/main/resources"));
		// 分析器:负责分析一个文件，并从将被索引的文本获取令牌/字
		Analyzer analyzer = new StandardAnalyzer();
		// 配置类
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_4,analyzer);
		/**
		 APPEND：总是追加，可能会导致错误，索引还会重复，导致返回多次结果
		 CREATE：清空重建（推荐）
		 CREATE_OR_APPEND【默认】：创建或追加
		 */
		iwc.setOpenMode(OpenMode.CREATE);// 创建模式 OpenMode.CREATE_OR_APPEND 添加模式
		//用于更新或创建索引
		IndexWriter writer =  new IndexWriter(dir, iwc);
		for (User u : User.getInitList()) {
			//Document:一个虚拟文档与字段，其中字段是可包含在物理文档的内容，元数据等对象
			//Field:最低单元或索引过程的起点。它代表其中一个键被用于识别要被索引的值的键值对关系。
			//用于表示一个文件内容的字段将具有键为“内容”，值可以包含文本或文档的数字内容的部分或全部
			Document doc = new Document();
			doc.add(new StringField("id", u.getId(), Store.YES));
			doc.add(new StringField("name",u.getName(), Store.YES));
			doc.add(new StringField("desc", u.getDesc(), Store.YES));
			System.out.println(doc);
			writer.addDocument(doc);
		}
		System.out.println("初始化完毕");
		writer.commit();
		writer.close();
	}

	public static void search() throws Exception {  
		Directory dir = FSDirectory.open(new File("src/main/resources"));
		IndexReader reader=DirectoryReader.open(dir);  
        IndexSearcher searcher=new IndexSearcher(reader);  
        //对于中文查询，需要分词
        //搜索的最低单位
        Term term=new Term("name", "张三"); 
        Query query= new TermQuery(term);  
        //模糊搜索
        //Term term=new Term("desc", "*小康*"); 
        //Query query= new WildcardQuery(term);  
        //相匹配的搜索条件的前N个搜索结果
        TopDocs topdocs=searcher.search(query, 2);  
        ScoreDoc[] scoreDocs=topdocs.scoreDocs;  
        System.out.println("查询结果总数:" + topdocs.totalHits);  
        System.out.println("最大的评分:"+topdocs.getMaxScore());  
        for(int i=0; i < scoreDocs.length; i++) {  
            int doc = scoreDocs[i].doc;  
            Document document = searcher.doc(doc);  
            System.out.println("docid:" + scoreDocs[i].doc);
            System.out.println("scors:" + scoreDocs[i].score);
            System.out.println("shardIndex:" + scoreDocs[i].shardIndex);
            System.out.println("id:"+document.get("id")+",name:"+document.get("name")+",desc:"+document.get("desc"));  
        }  
        reader.close();  
	}
}
