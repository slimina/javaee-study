package cn.slimsmart.java.lambda.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 应用
 */
public class Test7 {

    public static void main(String[] args) throws Exception {
        File file = new File("src/main/resources/test.log");
        BufferedReader read = new BufferedReader(new FileReader(file));
        List<String> logs = new ArrayList<>();
        String line = null;
        while((line = read.readLine())!=null){
            logs.add(line);
        }
        read.close();
        Map<String, Long> result = logs.parallelStream().filter(s->s.length() >0).map(s -> s.split("\\s+")[0].trim())
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        //排序
        List<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(result.entrySet());
        list= list.parallelStream().sorted((p1,p2)->{
            return Long.valueOf(p2.getValue() - p1.getValue()).intValue();
        }).collect(Collectors.toList());
        System.out.println(list);

        //https://www.baidu.com/s?wd=Collectors%E3%80%82groupingBy&rsv_spt=1&rsv_iqid=0xb8ef0e7000019610&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=1&rsv_t=6d620wTsT%2FapTkNnc5gXGAxKMVekyVwvLG%2FRBvwxnNT0%2FpTs3DMMyG1fMf%2Fc2DlXlG%2Fr&oq=groupingBy%2526lt%253Boncurrent&inputT=5923&rsv_sug3=6&rsv_pq=f7b1a6490002f99d&rsv_n=2&rsv_sug1=1&rsv_sug7=100&rsv_sug2=0&rsv_sug4=6720
        //http://blog.csdn.net/wild46cat/article/details/73477056
        //http://blog.csdn.net/wangmuming/article/details/72743790
        //http://blog.csdn.net/column/details/15695.html
        //http://blog.csdn.net/piglite/article/details/53900656
    }

}
