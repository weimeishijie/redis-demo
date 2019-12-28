package com.redis.enmu;

import java.time.ZoneId;

/**
 * Created by mj on 2018/1/20.
 *
 */
public enum TimeZone {

    ASIA_SHANGHAI("Asia/Shanghai");

    ZoneId id;

    TimeZone(String id){
        this.id = ZoneId.of(id);
    }

    public ZoneId getId(){
        return id;
    }

}
