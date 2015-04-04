package cn.slimsmart.memcache.demo.test.mq;

//接收消息回调
public interface MemcacheQCallback {
	//接收消息
	void receice(Object message);
}
