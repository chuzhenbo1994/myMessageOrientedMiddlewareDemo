package com.example.model.Redission.队列实战.普通队列;

import com.example.model.Redission.发布订阅模式.domain.UserLogDto;
import com.example.model.Redission.发布订阅模式.service.SysLogService;
import org.apache.logging.log4j.util.Strings;
import org.redisson.api.RQueue;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class QueueSubscriber implements ApplicationRunner, Ordered {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String key = "QueueRedissonKey";
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SysLogService sysLogService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("接收者开始监听消息 >>>>>>>>>>>>>");
        try {
            RQueue<String > queue = redissonClient.getQueue(key);

            while (true){
               String  poll = queue.poll();

               if (Strings.isNotBlank(poll)){
                   logger.info("queue收到的信息为:{}"+poll);
               }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("接受消息时出现了异常,信息为{}"+e.getMessage());
        }

    }
    @Override
    public int getOrder() {
        return -1;
    }
}
