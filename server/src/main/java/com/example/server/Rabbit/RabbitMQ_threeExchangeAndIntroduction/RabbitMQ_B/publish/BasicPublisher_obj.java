package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.RabbitMQ_B.publish;

import com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class BasicPublisher_obj {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;


    public void sentMessage(Person person) throws UnsupportedEncodingException {
        if (person != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.object.info.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.object.info.routing.key.name"));

                rabbitTemplate.convertAndSend(person, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties properties = message.getMessageProperties();
                        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Person.class);
                        return message;
                    }
                });
                logger.info("发送的信息为{}", person.toString());
            } catch (Exception e) {
                logger.info("发送失败，异常消息为 {}", e.getMessage());
            }
        }
    }
}
