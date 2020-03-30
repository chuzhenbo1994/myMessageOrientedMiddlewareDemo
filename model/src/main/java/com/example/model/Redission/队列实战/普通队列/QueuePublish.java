package com.example.model.Redission.队列实战.普通队列;

import com.example.model.Redission.发布订阅模式.domain.UserLogDto;
import org.redisson.api.RQueue;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueuePublish {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    static final String key = "QueueRedissonKey";

    @Autowired
    private RedissonClient redissonClient;

    public void sendMsg(String message) {
        try {
            RQueue<Object> queue = redissonClient.getQueue(key);

            queue.add(message);

            logger.info("发布者发布消息成功,消息为{}", message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
