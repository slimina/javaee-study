package cn.slimsmart.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.query.SortQueryBuilder;

public class SpringRedisTest extends BaseTest{
	
	public void valueOperationSample(){  
        ValueOperations<String, Order> valueOper = redisTemplate.opsForValue();  
        User suser = new User(1,"zhangsan",12);  
        valueOper.set("user:" + suser.getId(),suser);  
        System.out.println(valueOper.get("user:" + suser.getId()).getName());  
    }  
      
    public void listOperationSample(){  
        User suser = new User(1,"zhangsan",12);  
        ListOperations<String, User> listOper = redisTemplate.opsForList();  
        listOper.leftPush("user:list", suser);//lpush,head  
        listOper.rightPush("user:list", suser);//rpush,tail  
    }  
      
    public void boundValueOperationSample(){  
        User suser = new User(1,"zhangsan",12);  
        BoundValueOperations<String, User> bvo = redisTemplate.boundValueOps("user:" + suser.getId());  
        bvo.set(suser);  
        bvo.expire(60, TimeUnit.MINUTES);  
    }  
      
    /** 
     * 非连接池环境下，事务操作；对于sdr而言，每次操作(例如，get，set)都有会从pool中获取connection； 
     * 因此在连接池环境下，使用事务需要注意。 
     */  
    public void txUnusedPoolSample(){  
        User suser = new User(1,"zhangsan",12);  
        redisTemplate.watch("user:" + suser.getId());  
        redisTemplate.multi();  
        ValueOperations<String, User> tvo = redisTemplate.opsForValue();  
        tvo.set("user:" + suser.getId(), suser);  
        redisTemplate.exec();  
    }  
      
    /** 
     * 在连接池环境中，需要借助sessionCallback来绑定connection 
     */  
    public void txUsedPoolSample(){  
        SessionCallback<User> sessionCallback = new SessionCallback<User>() {  
            @Override  
            public User execute(RedisOperations operations) throws DataAccessException {  
                operations.multi();  
                User user = new User(2,"lisi",32);  
                String key = "user:" + user.getId();  
                BoundValueOperations<String, User> oper = operations.boundValueOps(key);  
                oper.set(user);  
                oper.expire(60, TimeUnit.MINUTES);  
                operations.exec();  
                return user;  
            }  
        };  
        redisTemplate.execute(sessionCallback);  
    }  
      
    /** 
     * pipeline : 1，正确使用方式 
     */  
    public void pipelineSample(){  
        final byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");  
        //pipeline  
        RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {  
            @Override  
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {  
                connection.openPipeline();  
                connection.incr(rawKey);  
                connection.incr(rawKey);  
                return connection.closePipeline();  
            }  
              
        };  
          
        List<Object> results = (List<Object>)redisTemplate.execute(pipelineCallback);  
        for(Object item : results){  
            System.out.println(item.toString());  
        }  
    }  
    //pipeline:备用方式  
    public void pipelineSampleX(){  
        byte[] rawKey = redisTemplate.getKeySerializer().serialize("user_total");  
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();  
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(factory);  
        List<Object> results;  
        try{  
            redisConnection.openPipeline();  
            redisConnection.incr(rawKey);  
            results = redisConnection.closePipeline();  
        }finally{  
            RedisConnectionUtils.releaseConnection(redisConnection, factory);  
        }  
        if(results == null){  
            return;  
        }  
        for(Object item : results){  
            System.out.println(item.toString());  
        }  
  
    }  
/** 
     * list/set排序，注意排序是根据value值的“字符”特性排序，如果value是java-object字节流，将不会有效 
     */  
    public void sort(){  
        ListOperations<String, String> listOper = redisTemplate.opsForList();  
        String listKey = "user:list";  
        listOper.leftPush(listKey, "zhangsan");  
        listOper.leftPush(listKey, "lisi");  
        listOper.leftPush(listKey, "wanger");  
        SortQueryBuilder<String> builder = SortQueryBuilder.sort(listKey);  
        builder.alphabetical(true);//对字符串使用“字典顺序”  
        builder.order(Order.DESC);//倒序  
        builder.limit(0, 2);  
        //builder.limit(new Range(0, 2));  
        List<String> results = redisTemplate.sort(builder.build());  
        for(String item : results){  
            System.out.println(item);  
        }  
    }  
}
