package cn.slimsmart.solr.demo.solrj;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

//采用POJOs方式处理查询结果
public class QueryDocsTest1 {
	
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
		
		SolrQuery query = new SolrQuery();
        query.setQuery("context:今天");
        query.setStart(0);
        query.setRows(2);
 
        QueryResponse response = client.query(query);
        // 搜索得到的结果数
        System.out.println("Find:" + response.getResults().getNumFound());
        // 输出结果
        int iRow = 1;
         
        SolrDocumentList list = response.getResults();
        DocumentObjectBinder binder = new DocumentObjectBinder();
        List<Student> beanList=binder.getBeans(Student.class, list);
        for(Student s:beanList){
            System.out.println(s.getId());
        }
        for (SolrDocument doc : response.getResults()) {
            System.out.println("----------" + iRow + "------------");
            System.out.println("id: " + doc.getFieldValue("id").toString());
            System.out.println("name: " + doc.getFieldValue("name").toString());
            iRow++;
        }
        client.close();
	}
}
