package com.example.model.Redission.configuration;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Bean
    public RedissonClient configs() {
        //创建配置实例
        Config config = new Config();
        // 可以设置传输模式为Epoll 也可以设置为NIO
     /*   config.setTransportMode(TransportMode.NIO);
        config.useClusterServers().addNodeAddress(env.getProperty("redisson.host.config"),
                env.getProperty("redisson.host.config"));*/
        // 使用的单一节点模式
        config.useSingleServer().setAddress(env.getProperty("redisson.host.config")).
                setKeepAlive(true);
        // 创建并返回操作redisson客户端.

        RedissonClient client = Redisson.create(config);
        logger.info("创建的redisson客户端是: "+client.toString());
        return client;
    }

}
