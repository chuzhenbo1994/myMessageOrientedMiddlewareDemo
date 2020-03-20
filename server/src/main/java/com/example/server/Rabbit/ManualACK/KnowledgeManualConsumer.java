package com.example.server.Rabbit.ManualACK;

import com.example.server.Rabbit.domain.KnowledgeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("knowledgeManualConsumer")
public class KnowledgeManualConsumer implements ChannelAwareMessageListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private ObjectMapper objectMapper =new ObjectMapper();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取消息属性
        MessageProperties messageProperties = message.getMessageProperties();

        // 获取消息分发的全局唯一标识
        long deliveryTag = messageProperties.getDeliveryTag();

        try {
            //获取消息体
            byte[] body = message.getBody();

            KnowledgeInfo info = objectMapper.readValue(body, KnowledgeInfo.class);
            logger.info("手动确认消费的内容是：", info);
            // 执行完逻辑后 手动进行确认消费，其中一个参数为： 消息的分发标志，第二个标志：是否允许批量确认消费。
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            logger.info("手动确认消费的异常内容是：", e.getMessage());
            channel.basicAck(deliveryTag, false);
        }
    }
}
