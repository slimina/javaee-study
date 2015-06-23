package cn.slimsmart.lucene.mmseg4j.simple;


/**
 * 测试
 * @author slimina
 * 2015年6月23日
 */
public class Test {

	public static String indexPath="src/main/resources/index";
	
	public static void main(String[] args) throws Exception, Exception, Exception {
		//收录网站，创建索引
		//Indexer.addSite("http://www.baidu.com/", indexPath);
		//Indexer.addSite("http://www.csdn.net/", indexPath);
		//Indexer.addSite("http://www.oschina.net/", indexPath);
		
		//搜索
		Searcher.searcher("有团队管理经验者优先考虑", indexPath);
	}
}
