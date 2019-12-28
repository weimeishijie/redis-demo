package com.redis.service;

import org.json.JSONString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by lwy on 2019/12/28.
 *
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void saveObjectTest(){
        String redisTest1 = "redis-test1";
        String object = "hello world";
        redisService.saveObject(redisTest1, object);
        String redisTest2 = "redis-test2";
        String json = "{name:li wen ya,age:22,job:java 编程}";
        redisService.saveObject(redisTest2, json);
        String redisTest3 = "redis-test3";
        Integer value = 0;
        redisService.saveObject(redisTest3, value);
    }

    @Test
    public void readObjectTest(){
        Object value1 = redisService.readObject("redis-test1");
        Object value2 = redisService.readObject("redis-test2");
        System.out.println(value1);
        System.out.println(value2);
    }

    @Test
    public void deleteObjectTest(){
        String key = "hello";
        redisService.deleteObject(key);
        List<String> keys = new ArrayList<>();
        keys.add("redis-test");
        keys.add("redis-test2");
        redisService.deleteObjects(keys);
    }

    @Test
    public void hasKayTest(){
        System.out.println(redisService.hasKey("redis-test"));
        System.out.println(redisService.hasKey("redis-test1"));
    }

    @Test
    public void expireKeyTest(){
        String expireKey = "redis-test1";
        Long times = 10L;
        redisService.expireKey(expireKey,times, TimeUnit.SECONDS);
    }

    @Test
    public void incrementTest(){
        String key = "redis-test3";
        redisService.increment(key);
    }

    @Test
    public void saveListTest(){
        String key = "redis-test4";
        List<String> strs = new ArrayList<>();
        strs.add("11");
        strs.add("21");
        strs.add("31");
        strs.add("41");
        strs.add("51");
        redisService.saveList(key, strs);
    }

    @Test
    public void readListTest(){
        List<?> list = redisService.readList("redis-test4");
        for (Object str : list) {
            System.out.println(str);
        }
    }




}
