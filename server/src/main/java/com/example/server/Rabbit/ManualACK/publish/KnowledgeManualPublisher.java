package com.example.server.Rabbit.ManualACK.publish;

import com.example.server.Rabbit.domain.KnowledgeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeManualPublisher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;

    public void sentAutoMessage(KnowledgeInfo knowledgeInfo) {

        try {
            if (knowledgeInfo != null) {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.manual.knowledge.exchange.name"));
                rabbitTemplate.setExchange(environment.getProperty("mq.manual.knowledge.routing.key.name"));
                Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(knowledgeInfo)).
                        setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(message);
                logger.info("手动确认的消息已发送 内容为{}" + message);
            }
        } catch (Exception e) {

            logger.info("手动确认的消息出现异常 信息为{}" + e.getMessage());
        }
    }
}
