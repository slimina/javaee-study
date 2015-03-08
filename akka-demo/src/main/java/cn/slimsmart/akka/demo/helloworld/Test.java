package cn.slimsmart.akka.demo.helloworld;

import scala.remote;
import akka.remote.RemoteActorRef;
import akka.remote.RemoteRef;
import akka.remote.Remoting;

public class Test {

	public static void main(String[] args) {
		akka.Main.main(new String[]{"cn.slimsmart.akka.demo.helloworld.HelloWorld"});
	}

}
