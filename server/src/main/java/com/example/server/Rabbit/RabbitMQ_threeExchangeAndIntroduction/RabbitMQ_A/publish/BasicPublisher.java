package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.RabbitMQ_A.publish;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.util.Strings;
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

import java.io.UnsupportedEncodingException;

@Component
public class BasicPublisher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;


    public void sentMessage(String message) throws UnsupportedEncodingException {
        if (Strings.isNotBlank(message)) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.basic.info.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.basic.info.routing.key.name"));
                Message build = MessageBuilder.withBody(message.getBytes("UTF-8")
                ).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                logger.info("发送的信息为{}", build);
                rabbitTemplate.convertAndSend(build);
            } catch (Exception e) {
                logger.info("发送失败，异常消息为 {}", e.getMessage());
            }
        }
    }
}
