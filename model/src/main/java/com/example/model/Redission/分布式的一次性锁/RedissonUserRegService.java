package com.example.model.Redission.分布式的一次性锁;

import com.example.model.Text.UserReg;
import com.example.model.Text.UserRegDto;
import com.example.model.Text.UserRegRepository;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonUserRegService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRegRepository regRepository;

    @Autowired
    private RedissonClient redissonClient;


    private static final String pathPrefix = "RedissonOneLock";


    public void userRegWithRedisson(UserRegDto userRegDto) throws Exception {
        // 构造参数
        // 使用redisson 区获取锁
        String key = pathPrefix + userRegDto.getUserName();
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock(20,TimeUnit.SECONDS);
            UserReg userName = regRepository.findUserRegByUserName(userRegDto.getUserName());
            if (userName == null) {
                UserReg userReg = new UserReg();
                BeanUtils.copyProperties(userRegDto, userReg);
                userReg.setCreateTime(new Date());
                UserReg returnReg = regRepository.save(userReg);
            } else {
                logger.info("用户名已经被注册{}", userRegDto.getUserName());
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }finally {
            if (lock!=null){
                lock.unlock();
                logger.info("分布式的一次性锁关闭");
            }
        }

    }
}
