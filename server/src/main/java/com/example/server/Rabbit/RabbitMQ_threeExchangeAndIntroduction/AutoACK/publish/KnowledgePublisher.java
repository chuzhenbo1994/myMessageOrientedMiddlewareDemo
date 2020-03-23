package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.AutoACK.publish;

import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.domain.KnowledgeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class KnowledgePublisher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;


    public void sentMessage(KnowledgeInfo knowledgeInfo) throws UnsupportedEncodingException {
        if (knowledgeInfo != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.auto.knowledge.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.auto.knowledge.routing.key.name"));

                rabbitTemplate.convertAndSend(knowledgeInfo, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties properties = message.getMessageProperties();
                        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, KnowledgeInfo.class);
                        return message;
                    }
                });
                logger.info("发送的信息为{}",knowledgeInfo.toString());
            } catch (Exception e) {
                logger.info("发送失败，异常消息为 {}", e.getMessage());
            }
        }
    }
}
