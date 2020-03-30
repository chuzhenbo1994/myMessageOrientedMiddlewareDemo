package com.example.model.Redission.队列实战.延迟发送队列;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DelayQueuePublish {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    static final String key = " DelayQueueRedissonKey";

    @Autowired
    private RedissonClient redissonClient;

    public void sendMsg(String message,final Long ttl) {
        try {
           // 设置阻塞队列
            RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(key);
            // 设置 延迟队列
            RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            // 设置 时间
            delayedQueue.offer(message,ttl, TimeUnit.MILLISECONDS);


            logger.info("发布者发布消息成功,消息为{}", message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
