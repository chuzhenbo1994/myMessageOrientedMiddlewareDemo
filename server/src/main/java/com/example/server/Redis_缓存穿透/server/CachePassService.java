package com.example.server.Redis_缓存穿透.server;

import com.example.server.Redis_缓存穿透.domain.Item;
import com.example.server.Redis_缓存穿透.repository.ItemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CachePassService {
    @Autowired
    private ItemRepository repository;

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String keyPrefix = "item:";

    /**
     * 缓存穿透的应用
     *
     * 导致的原理是：给db造成压力，甚至是击垮数据库。解决：尽量让高并发的读请求落到
     * 缓存中，从而避免直接跟数据库打交道。
     * @param ItemCode
     * @return
     * @throws JsonProcessingException
     */
    public Item getItemInfo(String ItemCode) throws JsonProcessingException {
        Item byCode = null;
        final String key = keyPrefix + ItemCode;
        ValueOperations opsForValue = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            Object o = opsForValue.get(key);
            if (o != null && Strings.isNotBlank(o.toString())) {
                byCode = objectMapper.readValue(o.toString(), Item.class);
                System.out.println(byCode.toString());
            }
        } else {
            byCode = repository.findItemByCode(ItemCode);
            if (byCode != null) {
                opsForValue.set(key, objectMapper.writeValueAsString(byCode));
            } else {
                opsForValue.set(key, "", 30L, TimeUnit.MINUTES);
            }
        }
        return byCode;

    }
}
