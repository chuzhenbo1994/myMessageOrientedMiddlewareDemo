package com.example.server.Redis.Redis_用户注册案例.service;

import com.example.server.Redis.Redis_用户注册案例.domain.UserReg;
import com.example.server.Redis.Redis_用户注册案例.domain.UserRegDto;
import com.example.server.Redis.Redis_用户注册案例.repository.UserRegRepository;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserRegService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRegRepository regRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void userRegNoLock(UserRegDto userRegDto) {
        UserReg user = regRepository.findUserRegByUserName(userRegDto.getUserName());
        try {
            logger.info("不加分布式的锁，当前的用户名为{}", userRegDto.getUserName());
            if (user == null) {
                UserReg userReg = new UserReg();
                BeanUtils.copyProperties(userRegDto, userReg);
                userReg.setCreateTime(new Date());
                UserReg returnReg = regRepository.save(userReg);
            }
        } catch (BeansException e) {
            e.printStackTrace();
            logger.info("用户名已经被注册{}", e.getMessage());
        }
    }

    public void userRegWithLock(UserRegDto userRegDto) {
        final String key = userRegDto.getUserName() + "-lock";
        // 获取一个时间戳
        final String value = System.nanoTime() + "" + UUID.randomUUID();
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        logger.info("插入redis里面的key是{}，value是{}", key, value);
        Boolean ifAbsent = opsForValue.setIfAbsent(key, value);
        // 如果是true 则获取到了分布式的锁
        if (ifAbsent) {
            //设置锁的过期时间
            try {
                redisTemplate.expire(key, 20L, TimeUnit.MINUTES);
                UserReg user = regRepository.findUserRegByUserName(userRegDto.getUserName());
                if (user == null) {
                    UserReg userReg = new UserReg();
                    BeanUtils.copyProperties(userRegDto, userReg);
                    userReg.setCreateTime(new Date());
                    UserReg returnReg = regRepository.save(userReg);
                } else {
                    logger.info("用户名已经被注册{}", userRegDto.getUserName());
                }
            } catch (BeansException e) {
                e.printStackTrace();
            } finally {
                // 无论如何 最后都需要加锁成功后操作后释放锁
                if (value.equals(opsForValue.get(key).toString())) {
                    redisTemplate.delete(key);
                }
            }

        }
    }

    // 服务中添加一个 zookeeper的分布式锁的一个功能;
    private CuratorFramework client;
    private static final String pathPrefix = "middleware/zkLock";


    public void userRegWithZKLock(UserRegDto userRegDto) throws Exception {
        // 创建zookeeper互斥锁组件 需要将监控用的客户端实例 精心构造的共享资源 作为
        // 构造参数

        InterProcessMutex mutex = new InterProcessMutex(client,
                pathPrefix + userRegDto.getUserName() + "-lock");
        // 采用互斥锁组件尝试获取分布式锁,其中尝试的最大时间在这里设置为 10 秒
        try {
            if (mutex.acquire(10L, TimeUnit.SECONDS)) {
                UserReg userName = regRepository.findUserRegByUserName(userRegDto.getUserName());
                if (userName == null) {
                    UserReg userReg = new UserReg();
                    BeanUtils.copyProperties(userRegDto, userReg);
                    userReg.setCreateTime(new Date());
                    UserReg returnReg = regRepository.save(userReg);
                }else {
                    logger.info("用户名已经被注册{}", userRegDto.getUserName());
                }
            }else {
                throw new Exception("获取zookeeper锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mutex.release();
        }

    }
}
