package cn.slimsmart.ehcache;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EhcacheTest {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Cache  ehCache = (Cache)context.getBean("ehCache");
		ehCache.put(new Element("key", "value"));
		ehCache.put(new Element("key1", "aaaa", 0, 10));
		BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
		String line=null;
		while ((line=strin.readLine())!=null && !"exit".equals(line)) {
			String[] ss = line.split("\\s+");
			if(ss.length==1){
				System.out.println(ehCache.get(ss[0]));
			}else if (ss.length==2){
				ehCache.put(new Element(ss[0],ss[1]));
				System.out.println("ok");
			}
		}
		strin.close();
	}

}
