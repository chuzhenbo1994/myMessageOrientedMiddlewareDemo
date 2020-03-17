package com.example.server.Redis_抢红包案例.service;

import com.example.server.Redis_抢红包案例.domain.RedPacketDto;
import com.example.server.Redis_抢红包案例.utils.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedPacketServiceImpl implements IRedPacketService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String keyPrefix = "redis:red:packet:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IRedService iRedService;

    @Override
    public String handOut(RedPacketDto packetDto) throws Exception {

        if (packetDto.getTotal() > 0 && packetDto.getAmount() > 0) {
            List<Integer> list = RedPacketUtil.divideRedPackage(packetDto.getAmount(), packetDto.getTotal());
            String timeValue = String.valueOf(System.nanoTime());
            String redId = new StringBuffer(keyPrefix).append(packetDto.getUserId()).append(":").append(timeValue).toString();
            // 将红包id 和 金额的总数放到缓存中
            Long aLong = redisTemplate.opsForList().leftPushAll(redId, list);

            // 将红包的总数放到redis中
            String redisTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redisTotalKey, packetDto.getTotal());

            // 异步将红包的全局唯一标志串 红包个数 与 随机金额列表信息至数据库中
            iRedService.recordRedPacket(packetDto, redId, list);
        }
        return null;
    }

    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        //处理用户抢红包之前，需要判断是否已经抢过红包。
        Object obj = opsForValue.get(redId + userId + ":rob");
        if (obj != null) {
            return new BigDecimal(obj.toString());
        }
        Boolean click = click(redId);
        if (click) {
            // 如果是抢到了
            //改进加锁 够着缓存中的key
            final String lockKey = redId + userId + "-lock";
            //改进加锁  调用setIfAbsent()方法，其实就是间接的实现了分布式的锁
            Boolean lock = opsForValue.setIfAbsent(lockKey, redId);
            //改进加锁 设置一个过期时间
            Boolean expire = redisTemplate.expire(lockKey, 24L, TimeUnit.HOURS);

            //改进加锁 对锁进行判断
            if (lock) {
                Object o = redisTemplate.opsForList().rightPop(redId);
                if (o != null) {
                    // 表示缓存金额有钱
                    String redTotalKey = redId + ":total";
                    Integer currentTotal = opsForValue.get(redTotalKey) != null ? (Integer) opsForValue.get(redTotalKey) : 0;

                    opsForValue.set(redTotalKey, currentTotal - 1);
                    BigDecimal result = new BigDecimal(o.toString()).divide(new BigDecimal(100));
                    // 将抢到的情况记录到数据库中。
                    iRedService.recordRobRedPacket(userId, redId, new BigDecimal(o.toString()));
                    // 将抢到红包的用户信息存入缓存信息 用于表示该用户已经抢到红包了
                    opsForValue.set(redId + userId + ":rob", result, 24L, TimeUnit.HOURS);

                    logger.info("当前用户抢到红包了：userId={} key={} 金额={}", userId, redId, result);
                    return result;
                }
            }
        }
        return null;
    }

    private Boolean click(String redId) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String redTotalKey = redId + ":total";
        Object total = opsForValue.get(redTotalKey);
        if (total != null && Integer.valueOf(total.toString()) > 0) {
            return true;
        }
        return false;
    }
}
