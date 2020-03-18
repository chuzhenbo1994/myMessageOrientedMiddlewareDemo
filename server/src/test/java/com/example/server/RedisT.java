package com.example.server;

import com.example.server.Redis.Redis_缓存穿透.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisT {
    //////////////////////////////////*案例一*/////////////////////////
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void one() {
        final String context = "redisTemplate實戰字符串信息";
        final String key = "redis：template：one：string";
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(key, context);
        Boolean aBoolean = redisTemplate.hasKey(key);
        Object o = ops.get(key);
        logger.info(o.toString());
    }
    /////////////////*案例二*////////////////////////


    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void two() throws JsonProcessingException {
        User user = new User(1, "dug", "阿修羅");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        final String context = objectMapper.writeValueAsString(user);
        final String key = "redis：template：two：object";
        valueOperations.set(key, context);
        Object o = valueOperations.get(key);
        User value = objectMapper.readValue(o.toString(), User.class);
        System.out.println(value);
    }

    @Test
    public void three() {
        List<User> list = new ArrayList<>();
        list.add(new User(2, "222", "222DDD"));
        list.add(new User(3, "333", "333DDD"));
        list.add(new User(4, "444", "444DDD"));
        final String key = "redis:text:list:object";
        ListOperations opsForList = redisTemplate.opsForList();
        list.forEach(a -> {
            opsForList.leftPush(key, a);
        });

        Object o = opsForList.rightPop(key);
        while (o != null) {
            User o1 = (User) o;
            o = opsForList.rightPop(key);
            System.out.println(o1);
        }
    }

    //////////  去重 使用set ////////////////
    @Test
    public void four() {
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("111");
        list.add("米好");
        list.add("abcd");
        list.add("abcd");
        list.add("大圣");
        list.add("大圣");
        logger.info("{}", list);
        final String key = "redis:text4：set";
        SetOperations forSet = redisTemplate.opsForSet();
        list.forEach(b -> {
            forSet.add(key, b);
        });
        Object pop = forSet.pop(key);
        while (pop != null) {
            System.out.println(pop.toString());
            pop = forSet.pop(key);
        }
    }
    @Test
    public void five(){
        List<User> list = new ArrayList<>();
        list.add(new User(5, "555", "555DDD"));
        list.add(new User(5, "555", "555DDD"));
        list.add(new User(6, "666", "666DDD"));
        list.add(new User(7, "777", "777DDD"));
        list.add(new User(6, "777", "777DDD"));
        final String key = "redis:text4：zset";
        ZSetOperations forZSet = redisTemplate.opsForZSet();
        list.forEach(a->{
            forZSet.add(key,a,a.getId());
        });
        Long size = forZSet.size(key);
        Set range = forZSet.range(key, 0L, size);
        range.forEach(b->{
            System.out.println(b.toString());
        });
    }
}
