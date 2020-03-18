package com.example.server.LginEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 监听信息的消费者 consumer类
 */
@Component
@EnableAsync
public class Consumer implements ApplicationListener<LoginEvent> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Async
    public void onApplicationEvent(LoginEvent loginEvent) {
        logger.info("收到的消息为 {} ", loginEvent);
    }
}
