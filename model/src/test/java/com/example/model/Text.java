package com.example.model;

import com.example.model.Redission.发布订阅模式.domain.UserLogDto;
import com.example.model.Redission.发布订阅模式.publish.RedissonUserLoginPublish;
import com.example.model.Redission.发布订阅模式.publish.UserLogSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Text {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
   private RedissonUserLoginPublish publish;
    @Test
    public void configInformation() {
        try {
            logger.info(redissonClient.getConfig().toJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void booleanFilter() {
        final String key = "myOne";
        //初始容量大小
        Long total = 100000L;

        RBloomFilter<Object> filter = redissonClient.getBloomFilter(key);
        // 预计统计元素的数量 和 期望的误差率
        filter.tryInit(total, 0.01);

        for (Long i = 0L; i < total; i++) {
            filter.add(i);
        }
        filter.add(1L);
        filter.add(2L);
        long size = filter.getSize();

        boolean contains1 = filter.contains(1L);
        boolean contains2 = filter.contains(-1L);
        boolean contains3 = filter.contains(0L);
        boolean contains4 = filter.contains(100000L);
        logger.info("长度是:" + size + "/n" + contains1 + "," + contains2 + "," + contains3 + "," + contains4);

    }

    @Test
    public void text3(){

        UserLogDto userLogDto = new UserLogDto();
        userLogDto.setUserName("chuZB");
        userLogDto.setPassWord("112233");
        userLogDto.setUserId(123123);
        publish.sendMsg(userLogDto);
    }
    @Test
    public void text4(){
        RMapDto dto = new RMapDto(0,"map0");
        RMapDto dto1 = new RMapDto(1,"map1");
        RMapDto dto2 = new RMapDto(2,"map2");
        RMapDto dto3 = new RMapDto(3,"map3");
        RMapDto dto4= new RMapDto(4,"map4");
        RMapDto dto5 = new RMapDto(5,"map5");
        RMap<Object, Object> map = redissonClient.getMap("mmm");
        map.put(dto.getI(),dto);
        map.putAsync(dto1.getI(),dto1);
        map.putIfAbsent(dto2.getI(),dto2);
        map.putIfAbsent(dto2.getI(),dto2);
        map.putIfAbsentAsync(dto3.getI(),dto3);

    }
}
