package com.example.server.RabbitMQ_B.consumer;

import com.example.server.RabbitMQ_B.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class BasicConsumer_obj {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.object.info.queue.name}", containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload Person person) {
        try {
            logger.info("消费者监听到的消息是{}", person.toString());
        } catch (Exception e) {
            logger.info("消费者监錯誤信息{}", e.getMessage());
        }
    }

}
