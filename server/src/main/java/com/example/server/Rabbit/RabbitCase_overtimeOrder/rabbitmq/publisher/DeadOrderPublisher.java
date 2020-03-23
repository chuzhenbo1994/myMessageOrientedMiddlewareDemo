package com.example.server.Rabbit.RabbitCase_overtimeOrder.rabbitmq.publisher;

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
public class DeadOrderPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void sendMsg(Integer orderId) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            // 因为死信队列是要把信息发送给 基本消息模型
            rabbitTemplate.setExchange(environment.getProperty("mq.producer.order.exchange.name"));
            // 设置基本路由
            rabbitTemplate.setRoutingKey("mq.producer.order.routing.key.name");
            /**
             * 发送对象类型的信息
             */
            rabbitTemplate.convertAndSend(orderId, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties = message.getMessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Integer.class);
                    return message;
                }
            });

            logger.info("用户下单，发送用户下单记录的id的信息进入死信队列-内容为 OrderId ={}", orderId);
        } catch (AmqpException e) {
            e.printStackTrace();
            logger.info("用户下单支付超时，发送用户下单记录的id的信息进入死信队列发生异常， OrderId ={}", orderId);
        }
    }
}
