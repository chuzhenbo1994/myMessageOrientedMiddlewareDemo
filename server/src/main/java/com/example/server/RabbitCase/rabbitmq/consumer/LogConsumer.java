package com.example.server.RabbitCase.rabbitmq.consumer;

import com.example.server.RabbitCase.domain.UserLogDto;
import com.example.server.RabbitCase.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class LogConsumer {

    @Autowired
    private SysLogService sysLogService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "${mq.login.queue.name}",containerFactory = "singleListenerContainer")
    public void loginConsumer(@Payload UserLogDto userLogDto){
        try {
            logger.info("获取到的登录日志 用户的信息为 {}",userLogDto.toString());
            //存入数据库中
            sysLogService.recordLog(userLogDto);
        } catch (Exception e) {
            logger.info("登录日志 用户的信息 出现异常 {}",e.getMessage());
        }
    }
}
