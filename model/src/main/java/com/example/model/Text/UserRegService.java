package com.example.model.Text;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserRegService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRegRepository regRepository;

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
