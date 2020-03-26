package com.example.model;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.model")
public class ModelApplication {
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(ModelApplication.class, args);
    }
    @Bean
    public CuratorFramework curatorFramework() {
        /**
         * 1 使用工程模式进行创建
         * 2 指定了指定客户端链接到zookeeper 服务端的策略,这里是采用重试机制(5次,每次间隔一秒)
         */
        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                builder().connectString(environment.getProperty("zk.host")).
                namespace(environment.getProperty("zk.namespace")).retryPolicy(new RetryNTimes(
                5, 1000)).build();
        curatorFramework.start();
        return curatorFramework;
    }
}
