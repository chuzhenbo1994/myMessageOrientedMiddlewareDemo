package com.example.server.Rabbit.DirectExchange.publish;

import com.example.server.Rabbit.domain.EventInfo;
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
public class DirectModelPublisher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;

    public void sendMegDirectOne(EventInfo eventInfo) {
        if (eventInfo != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.direct.routing.key.one.name"));
                rabbitTemplate.convertAndSend(eventInfo, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties properties = message.getMessageProperties();
                        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, EventInfo.class);
                        return message;
                    }
                });
                logger.info("发出的信息为{}", eventInfo.toString());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("发送消息时出现了异常{}", e.getMessage());
            }
        }
    }

    public void sendMegDirectTwo(EventInfo eventInfo) {
        if (eventInfo != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.direct.routing.key.two.name"));
                rabbitTemplate.convertAndSend(eventInfo, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties properties = message.getMessageProperties();
                        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, EventInfo.class);
                        return message;
                    }
                });
                logger.info("发出的信息为{}", eventInfo.toString());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("发送消息时出现了异常{}", e.getMessage());
            }
        }
    }
}
