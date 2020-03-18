package com.example.server.Rabbit.FanoutExchange.publish;

import com.example.server.Rabbit.FanoutExchange.domain.EventInfo;
import com.example.server.Rabbit.RabbitMQ_B.domain.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@Component
public class ModelPublisher {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;

    public void sendMeg(EventInfo eventInfo) {

        if (eventInfo != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.fanout.exchange.name"));
                rabbitTemplate.convertAndSend(eventInfo, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties properties = message.getMessageProperties();
                        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, EventInfo.class);
                        return message;
                    }
                });
               /* Message build = MessageBuilder.withBody(objectMapper.writeValueAsBytes(eventInfo)).build();
                rabbitTemplate.convertAndSend(build);
               ;*/
                logger.info("发出的信息为{}", eventInfo.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
