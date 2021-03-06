package com.example.server.Rabbit.RabbitCase_loginLog.service;

import com.example.server.Rabbit.RabbitCase_loginLog.domain.SysLog;
import com.example.server.Rabbit.RabbitCase_loginLog.domain.UserLogDto;
import com.example.server.Rabbit.RabbitCase_loginLog.repository.SysLogRepository;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@EnableAsync
public class SysLogService {
    @Autowired
    private SysLogRepository sysLogRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public void recordLog(UserLogDto userLogDto) {
        try {
            SysLog sysLog = new SysLog();
            sysLog.setUserId(userLogDto.getUserId());
            sysLog.setModule("用户登录模块");
            sysLog.setData(objectMapper.writeValueAsString(userLogDto));
            sysLog.setCreatTime(new Date());
            sysLogRepository.save(sysLog);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info("出入数据库的用户登录信息出现异常:{}", e.getMessage());
        }
    }
}
