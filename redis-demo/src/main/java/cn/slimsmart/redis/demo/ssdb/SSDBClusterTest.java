package cn.slimsmart.redis.demo.ssdb;

import com.lovver.ssdbj.SSDBJ;
import com.lovver.ssdbj.core.BaseResultSet;
import com.lovver.ssdbj.core.SSDBCmd;

@SuppressWarnings("unchecked")
public class SSDBClusterTest {
	public static void main(String[] args) throws Exception {
        //解析ssdbj.xml，查询ssdb_cluster，找到一个实例执行get key命令
		//SSDBCmd 支持ssdb命令
		//executeUpdate 添加更新
		BaseResultSet<byte[]> rs= SSDBJ.execute("ssdb_cluster",SSDBCmd.GET,"key");
		System.out.println(rs.getStatus());
        System.out.println(new String(rs.getResult()));

        if(SSDBJ.executeUpdate("ssdb_cluster",SSDBCmd.SET,"key","value22")){
        	rs= SSDBJ.execute("ssdb_cluster",SSDBCmd.GET,"key");
        	System.out.println(rs.getStatus());
            System.out.println(new String(rs.getResult()));
        }else{
        	System.out.println("executeUpdate fail.");
        }
	}
}