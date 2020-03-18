package com.example.server.LginEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于发送消息的 或者产生事件的生成者 producer
 * 主要是通过内置的ApplicationEventPublisher 组件实现消息的发送
 */
@Component
public class Producer {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApplicationEventPublisher publisher;


    public void setPublisher() {
        LoginEvent loginEvent = new LoginEvent(this, "debug", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "127.0.0.1");
        publisher.publishEvent(loginEvent);
        logger.info("发送的消息为 {}", loginEvent);
    }

}
