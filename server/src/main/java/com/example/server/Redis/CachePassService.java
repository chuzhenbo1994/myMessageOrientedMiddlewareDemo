package com.example.server.Redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    public Item getItemInfo(String ItemCode) throws JsonProcessingException {
        final String key = keyPrefix + ItemCode;
        ValueOperations opsForValue = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            Object o = opsForValue.get(key);
            if (o != null && Strings.isNotBlank(o.toString())) {
                Item item = objectMapper.readValue(o.toString(), Item.class);
                System.out.println(item.toString());
            }
        } else {
            Item byCode = repository.findItemByCode(ItemCode);
            if (byCode != null) {
                opsForValue.set(key, objectMapper.writeValueAsString(byCode));
            } else {
                opsForValue.set(key, "", 30L, TimeUnit.MINUTES);
            }
        }
        return null;

    }
}
