package cn.slimsmart.zeromq.demo.push_pull;

import org.zeromq.ZMQ;

//http://blog.csdn.net/kobejayandy/article/details/20163431
public class Push {

	public static void main(String[] args) {
		ZMQ.Context context = ZMQ.context(1);  
        ZMQ.Socket push  = context.socket(ZMQ.PUSH);  
        push.bind("ipc://msg_test");  
        for (int i = 0; i < 10000000; i++) {  
            push.send("hello".getBytes());  
        }  
        push.close();  
        context.term();
	}

}
