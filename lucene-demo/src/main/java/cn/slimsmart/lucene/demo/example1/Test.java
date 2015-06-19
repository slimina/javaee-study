package cn.slimsmart.lucene.demo.example1;

public class Test {

	public static void main(String[] args) throws Exception {
		System.out.println("创建索引开始");
		CreateIndex.write();
		System.out.println("搜索--Keyword");
		SearchKeyword.search("小康");
	}
}
