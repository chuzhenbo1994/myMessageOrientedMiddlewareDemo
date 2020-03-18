package com.example.server.Redis.Redis_抢红包案例.service;

import com.example.server.Redis.Redis_抢红包案例.domain.RedPacketDto;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface IRedService {
    void recordRedPacket(RedPacketDto packetDto, String redId, List<Integer> list);

    @Async
    @Transactional(rollbackFor = Exception.class)
    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception;
}
