package com.example.server.Rabbit.common;

import com.example.server.Rabbit.ManualACK.KnowledgeManualConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.description.NamedElement;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
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
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory containerFactory() {
        // 定义消息监听器所在的工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂做用到的实例
        factory.setConnectionFactory(connectionFactory);
        // 消息传输中的格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //并发消费者的初始数量为1
        factory.setConcurrentConsumers(1);
        //并发消费者实例的最大数量为1
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中 每个实例拉取的消息数量 为1
        factory.setPrefetchCount(1);
        return factory;
    }

    /**
     * 多个消费者实例的配置，高并发下
     *
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiContainerFactory() {
        // 定义消息监听器所在的工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factoryConfigurer.configure(factory, connectionFactory);
        // 消息传输中的格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置消息的确认消费模式 这里表示不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //并发消费者的初始数量为10
        factory.setConcurrentConsumers(10);
        //并发消费者实例的最大数量为15
        factory.setMaxConcurrentConsumers(15);
        //设置并发消费者实例中 每个实例拉取的消息数量 为10
        factory.setPrefetchCount(10);
        return factory;
    }

    /**
     * 单一的消费者
     *
     * @return
     */
    @Bean(name = "singleListenerContainerAuto")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
        // 定义消息监听器所在的工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂做用到的实例
        factory.setConnectionFactory(connectionFactory);
        // 消息传输中的格式
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //并发消费者的初始数量为1
        factory.setConcurrentConsumers(1);
        //并发消费者实例的最大数量为1
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中 每个实例拉取的消息数量 为1
        factory.setPrefetchCount(1);
        // 确认消费模式多设置 的一项 ACK
        // 设置确认消息模式为自动确认消费 AUTO
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        //设置消息发送确认机制-生产确认
        connectionFactory.setPublisherConfirms(true);
        //设置消息发送确认机制-发送成功返回反馈信息
        connectionFactory.setPublisherReturns(true);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        // 设置消息发送确认机制，即发送成功时打印日志
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                logger.info("消息发送成功：CorrelationData({}),ack({}),cause({})", correlationData, b, s);
            }
        });
        //设置消息发送确认机制，即发送完消息后打印反馈信息，如消息丢失等。
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                logger.info("消息发送丢失：exchange({}),route({}),replyCode({}),replyText({}),message({})", s1, s2, i, s, message);
            }
        });
        return rabbitTemplate;
    }

    @Autowired
    private Environment env;

    @Bean(name = "basicQueue")
    public Queue basicQueue() {
        return new Queue(env.getProperty("mq.basic.info.queue.name"), true);
    }

    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"), true, false);
    }

    // 创建一个绑定
    @Bean
    public Binding baseBinding() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(name = "objectQueue")
    public Queue objectQueue() {
        return new Queue(env.getProperty("mq.object.info.queue.name"), true);
    }

    @Bean
    public DirectExchange objectExchange() {
        return new DirectExchange(env.getProperty("mq.object.info.exchange.name"), true, false);
    }

    @Bean
    public Binding objectBinding() {
        return BindingBuilder.bind(objectQueue()).to(objectExchange()).with(env.getProperty("mq.object.info.routing.key.name"));
    }

    //创建 消息模式 FanoutExchange
    // 广播队列
    // Bean one
    @Bean(name = "fanoutQueueOne")
    public Queue fanoutQueueOne() {
        return new Queue(env.getProperty("mq.fanout.queue.one.name"), true);
    }

    // 广播队列
    // Bean two
    @Bean(name = "fanoutQueueTwo")
    public Queue fanoutQueueTwo() {
        return new Queue(env.getProperty("mq.fanout.queue.two.name"), true);
    }

    // 交换机
    // Bean
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(env.getProperty("mq.fanout.exchange.name"), true, false);
    }

    // 绑定  将广播交换机和队列绑定 1 对 2；
    // Bean 1
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    // Bean 2
    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    // 创建 消息模式  DirectExchange

    // 直接传输队列交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(env.getProperty("mq.direct.exchange.name"), true, false);
    }

    //创建队列1
    @Bean("directQueueOne")
    public Queue directQueueOne() {
        return new Queue(env.getProperty("mq.direct.queue.one.name"), true);
    }

    //创建队列2
    @Bean("directQueueTwo")
    public Queue directQueueTwo() {
        return new Queue(env.getProperty("mq.direct.queue.two.name"), true);
    }

    @Bean
    public Binding directBindingOne() {
        return BindingBuilder.bind(this.directQueueOne()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.one.name"));
    }

    @Bean
    public Binding directBindingTwo() {
        return BindingBuilder.bind(this.directQueueTwo()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.two.name"));
    }

    // 创建 消息模式  TopicExchange
    //创建交换机

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(env.getProperty("mq.topic.exchange.name"), true, false);
    }

    //创建 队列
    @Bean
    public Queue topicQueueOne() {
        return new Queue(env.getProperty("mq.topic.queue.one.name"), true);
    }

    @Bean
    public Queue topicQueueTwo() {
        return new Queue(env.getProperty("mq.topic.queue.two.name"), true);
    }

    // 创建绑定
    // 通配符为 *的路由
    @Bean
    public Binding topicBindingOne() {
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).
                with("mq.topic.routing.key.one.name");

    }

    // 通配符为 #的路由
    @Bean
    public Binding topicBindingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).
                with("mq.topic.routing.key.two.name");
    }

    // 自动确认的 队列
    @Bean("autoQueue")
    public Queue AUTOQueueOne() {
        return new Queue(env.getProperty("mq.auto.knowledge.queue.name"), true);
    }

    @Bean
    public DirectExchange AUTOExchange() {
        return new DirectExchange(env.getProperty("mq.auto.knowledge.exchange.name"), true, false);
    }

    @Bean
    public Binding AUTOBindingTwo() {
        return BindingBuilder.bind(AUTOQueueOne()).to(AUTOExchange()).
                with("mq.auto.knowledge.routing.key.name");
    }

    // 手动确认的 消息模式
    // 创建一个queue
    @Bean("manualQueue")
    public Queue manualQueue() {
        return new Queue(env.getProperty("mq.manual.knowledge.queue.name"), true);
    }

    // 创建交换机
    @Bean
    public TopicExchange manualExchange() {
        return new TopicExchange(env.getProperty("mq.manual.knowledge.exchange.name"), true, false);
    }

    // 创建绑定

    @Bean
    public Binding manualBinding() {
        return BindingBuilder.bind(manualQueue()).to(manualExchange()).
                with(env.getProperty("mq.manual.knowledge.routing.key.name"));
    }

    @Autowired
    public KnowledgeManualConsumer knowledgeManualConsumer;

    @Bean(name = "sampleContainerManual")
    public SimpleMessageListenerContainer listenerContainer(@Qualifier("manualQueue") Queue manualQueue) {
        //创建消息监听容器实例
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        //设置连接工厂
        container.setConnectionFactory(connectionFactory);
        // 消息传输中的格式-json格式
        /** 2.25 设置格式的没有了*/
        // 单一消费实例配置
        container.setConcurrentConsumers(1);
        //并发消费者实例的最大数量为1
        container.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中 每个实例拉取的消息数量 为1
        container.setPrefetchCount(1);
        // 设置消息的确认模式  手动确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 指定容器监听的队列
        container.setQueues(manualQueue);
        // 指定该容器的 消息监听器
        container.setMessageListener(knowledgeManualConsumer);
        return container;
    }

}
