package com.example.server.Redis_抢红包案例.service;

import com.example.server.Redis_抢红包案例.domain.RedPacketDto;

import java.math.BigDecimal;

public interface IRedPacketService {

    String handOut(RedPacketDto packetDto) throws Exception;

    BigDecimal rob(Integer userId,String redId) throws Exception;
}
