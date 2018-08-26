package com.lambert.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@ContextConfiguration(locations = {"classpath:spring/applicationContext-redis.xml",})
@RunWith(value = SpringJUnit4ClassRunner.class)
@Ignore
public class RedisTest {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Test
    public void test1() {
        ShardedJedis resource = shardedJedisPool.getResource();
        String test = resource.set("test", "123");
        System.out.println(test);
        resource.close();
        shardedJedisPool.close();
    }
}
