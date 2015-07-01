package cn.slimsmart.solr.demo.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

//普通方式处理查询结果
public class QueryDocsTest {
	
	static String URL = "http://192.168.18.119:9080/solr/test";
	public static void main(String[] args) throws SolrServerException, IOException {
		HttpSolrClient client = new HttpSolrClient(URL);
		client.setConnectionTimeout(5000);
		 //正常情况下，以下参数无须设置
        //使用老版本solrj操作新版本的solr时，因为两个版本的javabin incompatible,所以需要设置Parser
		client.setParser(new XMLResponseParser());
		client.setSoTimeout(1000); // socket read timeout
		client.setDefaultMaxConnectionsPerHost(100);
		client.setMaxTotalConnections(100);
		client.setFollowRedirects(false); // defaults to false
        // allowCompression defaults to false.
        // Server side must support gzip or deflate for this to have any effect.
		client.setAllowCompression(true);
		
		//使用ModifiableSolrParams传递参数
		/*  ModifiableSolrParams params = new ModifiableSolrParams();
      // http://192.168.18.119:9080/solr/test/select?q=context%3A%E4%BB%8A%E5%A4%A9&wt=json&indent=true
      // 设置参数，实现上面URL中的参数配置
      // 查询关键词
      params.set("q", "context:今天");
      // 返回信息
      params.set("fl", "id,name,age,desc");
      // 排序
      params.set("sort", "id asc");
      // 分页,start=0就是从0开始,rows=5当前返回5条记录,第二页就是变化start这个值为5就可以了
      params.set("start", 0);
      params.set("rows", 5);
      // 返回格式
      params.set("wt", "javabin");
      QueryResponse response = client.query(params);*/
		
		
        //使用SolrQuery传递参数，SolrQuery的封装性更好
		client.setRequestWriter(new BinaryRequestWriter());
        SolrQuery query = new SolrQuery();
        query.setQuery("context:今天");
        query.setFields("id","name","age","desc");
        query.setSort("id", ORDER.asc);
        query.setStart(0);
        query.setRows(2);
//      query.setRequestHandler("/select");
        QueryResponse response = client.query(query);
         
        // 搜索得到的结果数
        System.out.println("Find:" + response.getResults().getNumFound());
        // 输出结果
        int iRow = 1;
        for (SolrDocument doc : response.getResults()) {
            System.out.println("----------" + iRow + "------------");
            System.out.println("id: " + doc.getFieldValue("id").toString());
            System.out.println("name: " + doc.getFieldValue("name").toString());
            System.out.println("age: "+ doc.getFieldValue("age").toString());
            System.out.println("desc: " + doc.getFieldValue("desc"));
            iRow++;
        }
        client.close();
	}
}
