package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.RabbitMQ_A.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class BasicConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.basic.info.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload byte[] msg) {
        try {
            String s = new String(msg, "UTF-8");
            logger.info("消费者监听到的消息是{}", s);
        } catch (Exception e) {
            logger.info("消费者监錯誤信息{}", e);
        }
    }

}
