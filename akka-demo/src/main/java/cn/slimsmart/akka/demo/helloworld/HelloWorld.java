package cn.slimsmart.akka.demo.helloworld;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

//继承UntypedActor，表明实现的是一个Actor。
public class HelloWorld extends UntypedActor{
	
	//preStart是在启动这个Actor时调用的方法
	@Override
	   public void preStart() {
	       final ActorRef greeter =
	               getContext().actorOf(Props.create(Greeter.class), "greeter");
	       //调用tell方法给它发了一个消息，Greeter.Msg.GREET，
	       //后面的getSelf()给出了一个Actor的引用（ActorRef），用以表示发消息的Actor
	       greeter.tell(Greeter.Msg.GREET, getSelf());
	   }

	//接收到消息的情况
	@Override
	public void onReceive(Object msg) throws Exception {
		  if (msg == Greeter.Msg.DONE) {
			//getSelf()指明停下的目标
	           getContext().stop(getSelf());
	       } else {
	    	   //不处理
	           unhandled(msg);
	       }
	}

}
