package cn.slimsmart.redis.demo.nofity;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.List;

public class notifyTest {

    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");

        Jedis jedis = pool.getResource();
        config(jedis);

        jedis.psubscribe(new KeyExpiredListener(), "__keyevent@0__:expired");//过期队列
    }

    private static void config(Jedis jedis){
        String parameter = "notify-keyspace-events";
        List<String> notify = jedis.configGet(parameter);
        if(notify.get(1).equals("")){
            jedis.configSet(parameter, "Ex"); //过期事件
        }
    }
}

class KeyExpiredListener extends JedisPubSub {
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
    System.out.println(
        "pattern = [" + pattern + "], channel = [" + channel + "], message = [" + message + "]");
    //收到消息 key的键值，处理过期提醒
    }
}
