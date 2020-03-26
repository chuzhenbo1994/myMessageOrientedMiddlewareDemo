package com.example.model.zookeeper_lock_case.service;

import com.example.model.Text.UserReg;
import com.example.model.Text.UserRegDto;
import com.example.model.Text.UserRegRepository;
import com.example.model.zookeeper_lock_case.domain.BookDto;
import com.example.model.zookeeper_lock_case.domain.BookOrder;
import com.example.model.zookeeper_lock_case.repository.BookOrderRepository;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BookOrderRepository bookOrderRepository;

    // 服务中添加一个 zookeeper的分布式锁的一个功能;
    private CuratorFramework client;
    private static final String pathPrefix = "middleware/zkLock";


    public void bookOrderWithZKLock(UserRegDto userRegDto) throws Exception {
        // 创建zookeeper互斥锁组件 需要将监控用的客户端实例 精心构造的共享资源 作为
        // 构造参数
        InterProcessMutex mutex = new InterProcessMutex(client,
                pathPrefix + userRegDto.getUserName() + "-lock");
        // 采用互斥锁组件尝试获取分布式锁,其中尝试的最大时间在这里设置为 10 秒
        try {
            if (mutex.acquire(10L, TimeUnit.SECONDS)) {

                if (true) {

                } else {
                    logger.info("用户名已经被注册{}", userRegDto.getUserName());
                }
            } else {
                throw new Exception("获取zookeeper锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }

    }

    public Boolean findUserByBookOrder(BookDto bookDto) {

        List<BookOrder> bookOrder = bookOrderRepository.findBookOrdersByUserIdAndBookId(
                bookDto.getUserId(), bookDto.getBookId());
        if (bookOrder != null && bookOrder.size() > 0) {
            return false;
        }
        return true;
    }
}
