package com.syl.tb.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

    @Autowired(required = false)
    private ShardedJedisPool shardedJedisPool;

    private <T> T execute(Function<T,ShardedJedis> fun){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            return fun.callback(shardedJedis);
        }finally {
            if (shardedJedis != null){
                shardedJedis.close();
            }
        }

    }
    public String set(String key,String value){
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                return shardedJedis.set(key,value);
            }
        });
    }
    public String set(String key,String value,int seconds){
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                String str = shardedJedis.set(key,value);
                shardedJedis.expire(key,seconds);
                return str;
            }
        });
    }

    public String get(String key){
       return this.execute(new Function<String, ShardedJedis>() {
           @Override
           public String callback(ShardedJedis shardedJedis) {
               return shardedJedis.get(key);
           }
       });
    }

    public Long del(String key){
        return execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }
    public Long expire(String key,int seconds){
        return execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key,seconds);
            }
        });
    }
}
