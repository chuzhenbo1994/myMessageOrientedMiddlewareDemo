package com.example.model.Redission.发布订阅模式.service;


import com.example.model.Redission.发布订阅模式.domain.SysLog;
import com.example.model.Redission.发布订阅模式.domain.UserLogDto;
import com.example.model.Redission.发布订阅模式.repository.SysLogRepository;
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
