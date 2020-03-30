package com.example.server.Rabbit.RabbitDeadLetteQueue.consumer;

import com.example.server.Rabbit.RabbitDeadLetteQueue.entity.DeadInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadConsumer {
      private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.consumer.real.queue.name}", containerFactory = "singleListenerContainer")
    public void deadGetMsg(@Payload DeadInfo deadInfo) {
        try {
            logger.info("死信队列收到的消息为{}", deadInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("死信队列出現異常{}", e.getMessage());
        }

    }
}
