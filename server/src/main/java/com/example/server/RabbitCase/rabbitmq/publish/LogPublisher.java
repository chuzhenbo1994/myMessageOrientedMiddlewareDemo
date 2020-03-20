package com.example.server.RabbitCase.rabbitmq.publish;

import com.example.server.RabbitCase.domain.UserLogDto;

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

@Component
public class LogPublisher {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private ObjectMapper objectMapper;

    public void sendMsg(UserLogDto userLogDto) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(environment.getProperty("mq.login.exchange.name"));
            rabbitTemplate.setRoutingKey(environment.getProperty("mq.login.routing.key.name"));
            rabbitTemplate.convertAndSend(userLogDto, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties = message.getMessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, UserLogDto.class);
                    return message;
                }
            });
            logger.info("登录日志相关的信息已经发送给队列 内容{}", userLogDto.toString());
        } catch (AmqpException e) {
            e.printStackTrace();
            logger.info("登录日志相关的信息发送出现异常 内容{}", e.getMessage());
        }

    }
}
