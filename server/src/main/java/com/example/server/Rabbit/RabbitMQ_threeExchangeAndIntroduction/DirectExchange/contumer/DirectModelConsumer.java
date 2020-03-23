package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.DirectExchange.contumer;

import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.domain.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DirectModelConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "${mq.direct.queue.one.name}", containerFactory = "singleListenerContainer")
    public void conDMsgOne(@Payload EventInfo eventInfo) {
        try {

            logger.info("消息模式:通道一 ：监听消费的信息是：{} ", eventInfo);
        } catch (Exception e) {
            //  e.printStackTrace();
            logger.info("消息模式:通道一 ： 监听消费的异常信息是：{} ", e.getMessage());
        }
    }

    @RabbitListener(queues = "${mq.direct.queue.two.name}", containerFactory = "singleListenerContainer")
    public void conDMsgTwo(@Payload EventInfo eventInfo) {
        try {

            logger.info("消息模式:通道二 ：监听消费的信息是：{} ", eventInfo);
        } catch (Exception e) {
            //  e.printStackTrace();
            logger.info("消息模式:通道二 ： 监听消费的异常信息是：{} ", e.getMessage());
        }
    }
}
