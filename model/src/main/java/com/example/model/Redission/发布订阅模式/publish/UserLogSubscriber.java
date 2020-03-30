package com.example.model.Redission.发布订阅模式.publish;

import com.example.model.Redission.发布订阅模式.domain.SysLog;
import com.example.model.Redission.发布订阅模式.domain.UserLogDto;
import com.example.model.Redission.发布订阅模式.service.SysLogService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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

@Component
public class UserLogSubscriber implements ApplicationRunner, Ordered {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String key = "myOneRedissonKey";
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SysLogService sysLogService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("接收者开始监听消息 >>>>>>>>>>>>>");
        try {
            RTopic topic = redissonClient.getTopic(key);

            topic.addListener(UserLogDto.class, new MessageListener<UserLogDto>() {
                @Override
                public void onMessage(CharSequence charSequence, UserLogDto dto) {
                    if (dto != null) {
                        logger.info("接收到消息,准备将消息插入到数据库中 ");
                        sysLogService.recordLog(dto);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("接受消息时出现了异常,信息为{}"+e.getMessage());
        }

    }
    @Override
    public int getOrder() {
        return 0;
    }
}
