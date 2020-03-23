package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.AutoACK.constomer;

import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.domain.KnowledgeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 确认消费AUTO ACK的消费者
 */
@Component
public class KnowledgeConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //private ObjectMapper objectMapper =new ObjectMapper();

    @RabbitListener(queues = "${mq.auto.knowledge.queue.name}", containerFactory = "singleListenerContainerAuto")
    public void consumeMsg(@Payload KnowledgeInfo knowledgeInfo) {
        try {
            logger.info("消费者监听到的消息是{}", knowledgeInfo.toString());
        } catch (Exception e) {
            logger.info("消费者监錯誤信息{}", e.getMessage());
        }
    }

}
