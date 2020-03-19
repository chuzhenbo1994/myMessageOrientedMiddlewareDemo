package com.example.server.Rabbit.FanoutExchange.consumer;

import com.example.server.Rabbit.domain.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ModelConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "${mq.fanout.queue.one.name}", containerFactory = "singleListenerContainer")
    public void conFMsgOne(@Payload EventInfo eventInfo){
        try {

            logger.info("消息模式:通道一 ：监听消费的信息是：{} ",eventInfo);
        } catch (Exception e) {
          //  e.printStackTrace();
            logger.info("消息模式:通道一 ： 监听消费的异常信息是：{} ",e.getMessage());
        }
    }
    @RabbitListener(queues = "${mq.fanout.queue.two.name}", containerFactory = "singleListenerContainer")
    public void conFMsgTwo(@Payload EventInfo eventInfo){
        try {
         //   EventInfo eventInfo = mapper.readValue(msg, EventInfo.class);
            logger.info("消息模式:通道二 ： 监听消费的信息是：{} ",eventInfo);
        } catch (Exception e) {
            //  e.printStackTrace();
            logger.info("消息模式 :通道二 ：监听消费的异常信息是：{} ",e.getMessage());
        }
    }
}
