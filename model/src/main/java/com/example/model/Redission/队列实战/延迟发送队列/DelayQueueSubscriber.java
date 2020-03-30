package com.example.model.Redission.队列实战.延迟发送队列;

import com.example.model.Redission.发布订阅模式.service.SysLogService;
import org.apache.logging.log4j.util.Strings;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueSubscriber {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String key = "DelayQueueRedissonKey";
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 监听真正队列的消息 需要每时每刻监听
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void consumeMsg() {
        RBlockingDeque<String> deque = redissonClient.getBlockingDeque(key);

        try {
            String take = deque.take();
            if (Strings.isNotBlank(take)) {
                logger.info("收到的延迟队列的消息为{}", take);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("出现异常的情况{}", e.getMessage());
        }
    }


}
