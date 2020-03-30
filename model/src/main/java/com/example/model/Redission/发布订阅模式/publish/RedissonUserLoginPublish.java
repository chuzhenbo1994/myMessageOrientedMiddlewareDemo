package com.example.model.Redission.发布订阅模式.publish;

import com.example.model.Redission.发布订阅模式.domain.UserLogDto;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.K;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedissonUserLoginPublish {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    static final String key = "myOneRedissonKey";

    @Autowired
    private RedissonClient redissonClient;

    public void sendMsg(UserLogDto dto) {
        try {
            RTopic topic = redissonClient.getTopic(key);

            topic.publishAsync(dto);
            logger.info("发布者发布消息成功,消息为{}", dto.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
