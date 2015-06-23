package cn.slimsmart.lucene.mmseg4j.simple;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.FSDirectory;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

public class Indexer {

	// 已经存在的url列表
	private static List<String> urls = new ArrayList<String>();

	/**
	 * 索引器，对目标url创建索引
	 * 
	 * @param url
	 *            目标网址
	 * @param indexPath
	 * 			  索引目录
	 */
	private static void indexer(String url, String indexPath) throws IOException, ParserException {
		// 存储索引的目录
		File indexDir = new File(indexPath);
		// 目录不存在，创建该目录
		if (!indexDir.exists()) {
			indexDir.mkdir();
		}
		// 获取网页纯文本
		String content = getText(url);
		// 获取网页标题
		String title = getTitle(url);

		System.out.println("title:" + title);

		if (title == null || content == null || content.trim().equals("")) {
			return;
		}
		Document doc = new Document();
		FieldType fieldType = new FieldType();
		fieldType.setStored(true);
		fieldType.setIndexOptions(IndexOptions.NONE);
		// 加入url域
		doc.add(new Field("url", url, fieldType));
		// 加入标题域
		doc.add(new StringField("title", title, Field.Store.YES));
		// Index.ANALYZED分词后构建索引
		// 加入内容域
		doc.add(new TextField("content", content, Field.Store.YES));
		Analyzer analyzer = new ComplexAnalyzer();
		IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
		iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(FSDirectory.open(indexDir.toPath()), iwConfig);
		// 写入文档
		writer.addDocument(doc);
		// 关闭
		writer.close();
		// 创建了索引的网址加入到已经存在的网址列表中
		urls.add(url);
	}

	/**
	 * 获取网页纯文本
	 * @param url
	 *            目标网址
	 * @return
	 * @throws ParserException
	 */
	private static String getText(String url) throws ParserException {
		StringBean sb = new StringBean();
		// 设置不需要得到页面所包含的链接信息
		sb.setLinks(false);
		// 设置将不间断空格由正规空格所替代
		sb.setReplaceNonBreakingSpaces(true);
		// 设置将一序列空格由一个单一空格所代替
		sb.setCollapse(true);
		// 传入要解析的URL
		sb.setURL(url);
		// 返回解析后的网页纯文本信息
		String content = sb.getStrings();
		// System.out.println(content);
		return content;
	}

	/**
	 * 获取网页标题
	 * @param path
	 * @return
	 * @throws IOException
	 * @throws ParserException
	 */
	private static String getTitle(String path) throws IOException, ParserException {
		String title = "";
		try {
			Parser parser = new Parser(path);
			HtmlPage page = new HtmlPage(parser);
			parser.visitAllNodesWith(page);
			title = page.getTitle();
		} catch (Exception e) {
			title = "no title";
		}
		return title.trim();
	}

	/**
	 * 获取网页中所有的链接
	 * @param url
	 * @return
	 * @throws ParserException
	 */
	private static List<String> getLinks(String url) throws ParserException {
		List<String> links = new ArrayList<String>();
		// 创建链接节点的过滤器
		NodeFilter filter = new NodeClassFilter(LinkTag.class);
		Parser parser = new Parser();
		parser.setURL(url);
		// 设置目标网页的编码方式,java获取encode可以使用cpdetector
		//这里我们给一个默认值
		parser.setEncoding("UTF-8"); 
		NodeList list = parser.extractAllNodesThatMatch(filter);
		for (int i = 0; i < list.size(); i++) {
			LinkTag node = (LinkTag) list.elementAt(i);
			// 获取链接的目标网址
			String link = node.extractLink();
			if (link != null && !link.trim().equals("") && !link.equals("#")) {
				// 将目标网址加入到该页面的所有网址列表中
				links.add(link);
			}
		}
		return links;
	}

	/**
	 * 收录网站
	 * 
	 * @param url
	 *            网站首页url，也可以为网站地图url
	 * @param indexPath
	 * 			  索引目录
	 * @throws ParserException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void addSite(String url, String indexPath) throws ParserException, IOException, ParseException {
		long start = System.currentTimeMillis();
		System.out.println("start add...");
		// 获取目标网页的所有链接
		List<String> links = getLinks(url);
		System.out.println("url count:" + links.size());
		for (int i = 0; i < links.size(); i++) {
			String link = links.get(i);
			System.out.println((i + 1) + "." + link);
			if (!urls.contains(link)) {
				// 对未创建过索引的网页创建索引
				indexer(link, indexPath);
			} else {
				System.out.println("[" + link + "] exist");
			}
		}
		System.out.println("end...");
		long end = System.currentTimeMillis();
		System.out.println("cost " + (end - start) / 1000 + " seconds");
	}
}
