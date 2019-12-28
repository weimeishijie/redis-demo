package com.redis.test;

import com.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mj on 2018/1/20.
 *
 */
@Slf4j
@Controller
public class TestRedis {

    private final RedisService redisService;

    @Autowired
    public TestRedis(RedisService redisService) {
        this.redisService = redisService;
    }

//    @Scheduled(cron = "0/30 * * ? * *")
//    public void testIncrement(){
//        // 测试自增数据 aaa
//        if(redisService.hasKey("aaa")) {
//            System.out.println("有对象对其自增");
//            redisService.incre("aaa");
//        } else {
//            redisService.saveObject("aaa", 1);
//            // 测试过期时间
//            redisService.expireAtSchedTime("bbb", "09:34:29");
//            System.out.println("无对象对其创建");
//        }
//        // 测试保存对象 ccc
//        redisService.saveObject("ccc", 1);
//        // 测试保存集合 ddd
//        List<String> list = new ArrayList<String>();
//        list.add("list1");
//        list.add("list2");
//        list.add("list3");
//        redisService.saveList("ddd", list);
//        // 测试读取对象
//        redisService.pushEnd("ccc", "123");
//        redisService.pushEnd("bbb", "12");
//        System.out.println(redisService.readObject("bbb"));
//        // 测试删除对象
//        if(redisService.hasKey("ddd")){
//            redisService.deleteObject("ddd");
//            System.out.println("删除(集合)完成");
//        }
//        // 测试删除集合
//        List<String> list1 = new ArrayList<String>();
//        list1.add("list1a");
//        list1.add("list1b");
//        redisService.deleteObjects(list1);
//    }

}
