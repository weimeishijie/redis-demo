package com.redis.service;

import com.redis.enmu.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * Created by mj on 2018/1/20.
 *
 */
@Slf4j
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存一个任意对象（String, Integer, object等）
     * @param key 要存放的key （如果没有则在redis中自动创建一个key）
     * @param object 要存放的对象
     */
    void saveObject(String key, Object object){
        log.debug("Save data to redis, key:{}, value:{}", key, object);
        redisTemplate.opsForValue().set(key, object);
    }

    /**
     * 读取一个key的值
     * @param key 要读取得key
     * @return 返回一个对象
     */
    Object readObject(String key){
        log.debug("Read data from redis, key:{}", key);
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除一对key-value值对象的 (如果key不存在依然不会报错)
     * @param key 要删除的key
     */
    void deleteObject(String key){
        log.debug("Delete data from redis, key:{}", key);
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key （如果keys值不存在依然会执行含有的删除）
     * @param keys 要删除key的集合
     */
    public void deleteObjects(Collection<String> keys){
        log.debug("Delete data from redis, keys", keys);
        redisTemplate.delete(keys);
    }

    /**
     * 判断是否有 key
     * @param key 要检测的key
     * @return 有则返回 true 否则返回 false
     */
    boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置一个过期时间
     * @param key 指定的key
     * @param times 设置时常
     * @param unit 单位
     */
    void expireKey(String key, Long times, TimeUnit unit){
        log.debug("expire key in redis, key{}", key);
        redisTemplate.expire(key, times, unit);
    }

    /**
     * 给制定的key是指一个失效的时间点
     * @param key 要制定的 key
     * @param time 输出格式（24小时制 格式为 HH:mm:ss）
     *            如果制定的时间已是过去时间则加一天
     */
    public void expireAtSchedTime(String key, String time){
        Long now = ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId()).toEpochSecond();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(LocalDate.now(TimeZone.ASIA_SHANGHAI.getId()) + " " +time);
            Long existSeconds = (date.getTime() / 1000 - now);
            if(existSeconds < 0){
                date = format.parse(LocalDate.now(TimeZone.ASIA_SHANGHAI.getId()).plusDays(1) + " " + time);
                existSeconds = (date.getTime() / 1000 - now);
            }
            redisTemplate.expire(key, existSeconds, TimeUnit.SECONDS);
        } catch (ParseException e) {
            log.error("redis expire parse time error: {}", e);
        }
    }

    /**
     * 给制定的key值自增一
     * @param key 要指定的 key
     *            注意： key的数值一定是数字类型的且key值如果不存在会报错
     */
    void increment(String key){
        if(hasKey(key)){
            log.debug("increase a key value : ", key);
            redisTemplate.opsForValue().increment(key, 1);
        } else {
            log.info("输入的key值不存在");
        }
    }

    /**
     * 保存一个集合
     * @param key 要保存的 key
     * @param list 要保存的集合
     */
    void saveList(String key, List<?> list){
        log.debug("save a list in redis, key : {}", key);
        redisTemplate.delete(key);
        for(Object l : list){
            redisTemplate.opsForList().rightPush(key, l);
        }
    }

    /**
     * 读取一个key中所有的值
     * @param key 要读取得key值
     * @return 返回一个集合
     */
    public List<?> readList(String key){
        log.debug("read a list from redis, key : {}", key);
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 获得list第一个元素
     * @param key 读取第一个list的值
     * @return 返回一个对象
     */
    public Object readFirstFromList(String key){
        if(hasKey(key)){
            return redisTemplate.opsForList().range(key,0,0).get(0);
        } else {
            return null;
        }
    }

    /**
     * 放入队列的最后
     * @param key 要放入的key
     * @param value 要放入的值
     */
    public void pushEnd(String key, Object value){
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 取队列里面的最后一个元素
     */
    public Object popEnd(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 放入队列的第一个位置
     */
    public void pushFirst(String key, Object value){
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 取队列里面的第一个元素
     */
    public Object popFirst(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

}
