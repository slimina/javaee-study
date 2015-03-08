package cn.slimsmart.akka.demo.helloworld;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {
	
	public static enum Msg {
	       GREET, DONE
	   }
	
	   @Override
	   public void onReceive(Object msg) {
	       if (msg == Msg.GREET) {
	    	   //给发送者回复一条Msg.DONE
	           System.out.println("Hello World!");
	           getSender().tell(Msg.DONE, getSelf());
	       } else {
	           unhandled(msg);
	       }
	   }
}
