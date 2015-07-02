package cn.slimsmart.solr.demo.solrcloud;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class HighlightQuery {

	private static CloudSolrClient cloudSolrClient;

	private static synchronized CloudSolrClient getCloudSolrClient(final String zkHost) {
		if (cloudSolrClient == null) {
			try {
				cloudSolrClient = new CloudSolrClient(zkHost);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cloudSolrClient;
	}

	public static void main(String[] args) throws IOException, SolrServerException {
		final String zkHost = "192.168.100.90:2181";
		final String defaultCollection = "testcollection";
		final int zkClientTimeout = 20000;
		final int zkConnectTimeout = 1000;
		CloudSolrClient cloudSolrClient = getCloudSolrClient(zkHost);
		cloudSolrClient.setDefaultCollection(defaultCollection);
		cloudSolrClient.setZkClientTimeout(zkClientTimeout);
		cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
		cloudSolrClient.connect();

		SolrQuery params = new SolrQuery();
		String keyword = "小康";
		// the common parameters for all search
		params.set("q", keyword);
		params.set("fl", "*,score"); // field list 返回字段
		params.set("start", "0"); // 分页
		params.set("rows", "10");
		params.setHighlightSimplePre("<em>");
		params.setHighlightSimplePost("</em>");
		params.addHighlightField("name");
		params.addHighlightField("desc");
		params.setHighlight(true);
		params.setHighlightFragsize(72);
		params.setHighlightSnippets(3);
		params.setSort("score", ORDER.desc); // 排序
		//params.setSort("age", ORDER.desc);
		params.set("timeAllowed", "30000"); // miliseconds
		params.set("wt", "json"); // the response writer type 返回数据类型
		// 查询并获取相应的结果！
		QueryResponse response = null;
		response = cloudSolrClient.query(params);
		SolrDocumentList results = response.getResults();
		// 高亮集合
		Map<String, Map<String, List<String>>> highlightresult = response.getHighlighting();
		for (int i = 0; i < results.size(); ++i) {
			SolrDocument document = results.get(i);
			System.out.println(document.get("id").toString());

			if (highlightresult.get(document.get("id").toString()) != null && highlightresult.get(document.get("id").toString()).get("name") != null) {
				String t = highlightresult.get(document.get("id").toString()).get("name").get(0);
				System.out.println(t);
			} else {
				System.out.println(document.get("name"));
			}

			if (highlightresult.get(document.get("id").toString()) != null && highlightresult.get(document.get("id").toString()).get("desc") != null) {
				String t = highlightresult.get(document.get("id").toString()).get("desc").get(0);
				t = t.length() > 72 ? t.substring(0, 72) : t;
				System.out.println(t);
			} else {
				System.out.println(document.get("desc"));
			}
			System.out.println(document.get("score"));
		}
		cloudSolrClient.close();
	}
}
