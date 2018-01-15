package cn.slimsmart.java.lambda.hello;

import java.util.Optional;

/**
 * 选项接口(Optionals)一种特殊的工具用来解决NullPointerException
 * 使用参考文章：https://my.oschina.net/liuyatao19921025/blog/1605614
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<String> optional = Optional.of("abc"); 
        System.out.println( optional.isPresent());
        System.out.println(optional.get());
        System.out.println(optional.orElse("test")); //当Optional是空的时候orElse()可以返回一个默认值

        optional.ifPresent((s)-> System.out.println(s)); //查看Optional对象是否真的存在

        /*
        Optional.empty(); //创建一个空的Optional对象
        Optional<T> of(T value) //创建一个包含非null值的Optional
        Optional.ofNullable(T value); //创建一个可能包含null引用的Optional对象
        optional.filter(Predicate<? super T> predicate);
        optional.flatMap(Function<? super T, Optional<U>> mapper); //使用flatMap函数传递Optional对象
        optional.map(Function<? super T, Optional<U>> mapper); //使用map方法进行数据的提取和转化
        optional.orElseGet(Supplier<? extends T> other);
        optional.orElseThrow(Supplier<? extends X> exceptionSupplier); //当Optional为空的时候也可以使用orElseThrow()抛出异常
        */
        
        /*
        public class Computer {
		  private Soundcard soundcard;
		  public Soundcard getSoundcard() { ... }
		  ...
		}
		
		public class Soundcard {
		  private USB usb;
		  public USB getUSB() { ... }
		
		}
		
		public class USB{
		  public String getVersion(){ ... }
		}
		
        String version = computer.getSoundcard().getUSB().getVersion();
        
        java 8之前，可能
        String version = "UNKNOWN";
		if(computer != null){
	        Soundcard soundcard = computer.getSoundcard();
	        if(soundcard != null){
	             USB usb = soundcard.getUSB();
	             if(usb != null){
	                 version = usb.getVersion();
	                }
	         }
		 }
        
        //java 8 Optional
         
       public class Computer {
		  private Optional<Soundcard> soundcard;
		  public Optional<Soundcard> getSoundcard() { ... }
		  ...
		}
		
		public class Soundcard {
		  private Optional<USB> usb;
		  public Optional<USB> getUSB() { ... }
		
		}
		
		public class USB{
		  public String getVersion(){ ... }
		}
	
        String name = computer.flatMap(Computer::getSoundcard)
                          .flatMap(Soundcard::getUSB)
                          .map(USB::getVersion)
                          .orElse("UNKNOWN");
        
        */
    }
}
