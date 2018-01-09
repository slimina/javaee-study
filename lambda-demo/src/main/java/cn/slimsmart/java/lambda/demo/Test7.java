package cn.slimsmart.java.lambda.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 应用
 */
public class Test7 {

    public static void main(String[] args) throws Exception {
    	//读取文件
        File file = new File("src/main/resources/test.log");
        BufferedReader read = new BufferedReader(new FileReader(file));
        List<String> logs = new ArrayList<>();
        String line = null;
        while((line = read.readLine())!=null){
            logs.add(line);
        }
        read.close();
        
        //计算
        Map<String, Long> result = logs.parallelStream().filter(s->s.length() >0).map(s -> s.split("\\s+")[0].trim())
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        
        //排序
        Map<String, Long> sortMap = new LinkedHashMap<>();
        result.entrySet().parallelStream().sorted((p1,p2)->{
            return Long.valueOf(p2.getValue() - p1.getValue()).intValue();
        }).forEachOrdered(e->sortMap.put(e.getKey(), e.getValue()));
        
        sortMap.forEach((k,v)->System.out.println(k+":"+v));
    }
}
