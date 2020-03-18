package com.example.server.RabbitMQ.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class RabbitmqConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    // 自动装配连接rabbitmq的连接工厂实例
    @Autowired
    private CachingConnectionFactory connectionFactory;
    // 消息监听器所在的容器工程类实例
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一的消费者
     *
     * @return
     */
    @Bean(name = "singleContainer")
    public SimpleRabbitListenerContainerFactory containerFactory() {
        // 定义消息监听器所在的工厂
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂做用到的实例
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        // 消息传输中的格式
        listenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        //并发消费者的初始数量为1
        listenerContainerFactory.setConcurrentConsumers(1);
        //并发消费者实例的最大数量为1
        listenerContainerFactory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中 每个实例拉取的消息数量 为1
        listenerContainerFactory.setPrefetchCount(1);
        return listenerContainerFactory;
    }

    /**
     * 多个消费者实例的配置，高并发下
     *
     * @return
     */
    @Bean(name = "multiContainer")
    public SimpleRabbitListenerContainerFactory multiContainerFactory() {
        // 定义消息监听器所在的工厂
        SimpleRabbitListenerContainerFactory listenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factoryConfigurer.configure(listenerContainerFactory, connectionFactory);
        // 消息传输中的格式
        listenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());

        // 设置消息的确认消费模式 这里表示不需要确认消费
        listenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.NONE);

        //并发消费者的初始数量为10
        listenerContainerFactory.setConcurrentConsumers(10);
        //并发消费者实例的最大数量为15
        listenerContainerFactory.setMaxConcurrentConsumers(15);
        //设置并发消费者实例中 每个实例拉取的消息数量 为10
        listenerContainerFactory.setPrefetchCount(10);
        return listenerContainerFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                logger.info("消息发送成功：CorrelationData({}),ack({}),cause({})", correlationData, b, s);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                logger.info("消息发送丢失：exchange({}),route({}),replyCode({}),replyText({}),message({})", s1, s2, i, s, message);
            }
        });
        return rabbitTemplate;
    }
}
