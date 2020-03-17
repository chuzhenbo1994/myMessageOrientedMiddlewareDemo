package com.example.server.Redis_抢红包案例.service;

import com.example.server.Redis_抢红包案例.domain.RedPacketDto;
import com.example.server.Redis_抢红包案例.utils.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RedPacketServiceImpl implements IRedPacketService {
    private  final Logger logger  = LoggerFactory.getLogger(getClass());

    private static final  String keyPrefix = "redis:red:packet:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IRedService iRedService;
    @Override
    public String handOut(RedPacketDto packetDto) throws Exception {

        if (packetDto.getTotal()>0&&packetDto.getAmount()>0){
            List<Integer> list = RedPacketUtil.divideRedPackage(packetDto.getAmount(), packetDto.getTotal());
            String timeValue = String.valueOf(System.nanoTime());
            String redId = new StringBuffer(keyPrefix).append(packetDto.getUserId()).append(":").append(timeValue).toString();
           // 将红包id 和 金额的总数放到缓存中
            redisTemplate.opsForList().leftPushAll(redId,list);

            // 将红包的总数放到redis中
            String redisTotalKey = redId + "total";
            redisTemplate.opsForValue().set(redisTotalKey,packetDto.getTotal());

            // 异步将红包的全局唯一标志串 红包个数 与 随机金额列表信息至数据库中
            iRedService.recordRedPacket(packetDto,redId,list);
        }
        return null;
    }

    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        return null;
    }
}
