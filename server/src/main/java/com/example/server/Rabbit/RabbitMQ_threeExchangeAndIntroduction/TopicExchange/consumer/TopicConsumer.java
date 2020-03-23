package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.TopicExchange.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class TopicConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "${mq.topic.queue.one.name}", containerFactory = "singleListenerContainer")
    public void consumeTopicMsgOne(@Payload byte[] msg) throws UnsupportedEncodingException {
        String message = new String(msg, "utf-8");
        logger.info("通道一接受到的信息为：{}", message);
    }

    @RabbitListener(queues = "${mq.topic.queue.two.name}", containerFactory = "singleListenerContainer")
    public void consumeTopicMsgTwo(@Payload byte[] msg) throws UnsupportedEncodingException {
        String message = new String(msg, "utf-8");
        logger.info("通道二 接受到的信息为：{}", message);
    }
}
