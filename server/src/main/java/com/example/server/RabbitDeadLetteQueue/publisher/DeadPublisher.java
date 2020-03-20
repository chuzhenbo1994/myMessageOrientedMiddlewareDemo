package com.example.server.RabbitDeadLetteQueue.publisher;

import com.example.server.RabbitDeadLetteQueue.entity.DeadInfo;
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
public class DeadPublisher {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    public void deadSendMsg(DeadInfo deadInfo) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(environment.getProperty("mq.producer.basic.exchange.name"));
        rabbitTemplate.setRoutingKey("mq.producer.basic.routing.key.name");
        rabbitTemplate.convertAndSend(deadInfo, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties properties = message.getMessageProperties();
                properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, DeadInfo.class);
                properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                properties.setExpiration(String.valueOf(1000 * 20));
                return message;
            }
        });
        logger.info("死信队列 发送对象类型的消息进入死信队列,基本队列中，内容为{}", deadInfo.toString());
    }
}
