package cn.slimsmart.apache.directmemory;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.directmemory.DirectMemory;
import org.apache.directmemory.cache.Cache;

/**
 * Apache DirectMemory 是一个多层的缓存系统，特性包括无堆的内存管理用于支持大规模的 Java 对象，而不会影响 JVM
 * 垃圾收集器的性能。 官网：http://directmemory.apache.org/
 * 项目：https://github.com/raffaeleguidi/DirectMemory
 */
public class Test {

	public static void main(String[] args) throws Exception {
		/*
		 * numberOfBuffers 缓冲区数目
		 * size  缓存大小
		 * initialCapacity 初始容量
		 * concurrencyLevel 并发级别，guava new MapMaker().concurrencyLevel(4)
		 */
		Cache.init(10, 100, DirectMemory.DEFAULT_INITIAL_CAPACITY, DirectMemory.DEFAULT_CONCURRENCY_LEVEL);
		Cache.put("key1", "abc");
		Cache.put("key2", 123);
		Cache.put("key3", new Date());
		Cache.put("key4", "abc",1000);
		
		/*
		 * DirectMemory是java nio引入的，直接以native的方式分配内存，不受jvm管理。这种方式是为了提高网络和文件IO的效率，
		 * 避免多余的内存拷贝而出现的。DirectMemory 的默认大小是64M，而JDK6之前和JDK6的某些版本的SUN JVM，存在一个BUG，
		 * 在用-Xmx设定堆空间大小的时候，也设置了DirectMemory的大小。加入设置了-Xmx2048m，那么jvm最终可分配的内存大小为4G多一些，
		 * 是预期的两倍。解决方式是设置jvm参数-XX:MaxDirectMemorySize=128m，指定DirectMemory的大小。
		 * DirectMemory占用的大小没有直接的工具或者API可以查看，不过这个在Bits类中是有两个字段存储了最大大小和已分配大小的。
		 */
		Class<?> c = Class.forName("java.nio.Bits");
		Field maxMemory = c.getDeclaredField("maxMemory");
		maxMemory.setAccessible(true);
		Field reservedMemory = c.getDeclaredField("reservedMemory");
		reservedMemory.setAccessible(true);
		Long maxMemoryValue = (Long) maxMemory.get(null);
		Long reservedMemoryValue = (Long) reservedMemory.get(null);
		System.out.println("maxMemoryValue:" + maxMemoryValue);
		System.out.println("reservedMemoryValue:" + reservedMemoryValue);
		
		System.out.println("key1="+Cache.retrieve("key1"));
		System.out.println("key2="+Cache.retrieve("key2"));
		System.out.println("key3="+Cache.retrieve("key3"));
		System.out.println("key4="+Cache.retrieve("key4"));
		
		Thread.sleep(1000);
		System.out.println("******************************************");
		System.out.println("key1="+Cache.retrieve("key1"));
		System.out.println("key4="+Cache.retrieve("key4"));
	}

}
/**
 * 存在的问题： Cache类是单例，在同一个JVM里，不能根据实际应用创建不同的Cache（可以实现自己的Cache）；
 * OffHeapMemoryBuffer中ByteBuffer 空间存在浪费； 要根据业务类型，合理分配OffHeapMemoryBuffer的容量；
 * 目前Map<key>还是存放在Heap里，只是Value存放在Off-Heap，不过可以根据需要修改代码；
 * 
 * Direct-Memory依赖的其他类库 JoSQL SQL for Java Objects http://josql.sourceforge.net/
 * guava google-common-collection http://code.google.com/p/guava-libraries/其中的MapMaker的使用
 */
