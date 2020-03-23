package com.example.server.Rabbit.RabbitCase_overtimeOrder.service;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.MqOrder;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrder;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrderDto;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.rabbitmq.DeadOrderPublisher;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.repository.MqOrderJpaRepository;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.repository.UserOrderRepository;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeadOrderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private MqOrderJpaRepository mqOrderJpaRepository;

    @Autowired
    private DeadOrderPublisher deadOrderPublisher;

    public void pushUserOrder(UserOrderDto userOrderDto) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(userOrderDto, userOrder);
        // 设置支付的状态
        userOrder.setStatus(1);
        //设置下单的时间
        userOrder.setCreatTime(new Date());

        UserOrder save = userOrderRepository.save(userOrder);

        logger.info("成功下单，订单信息为{}", save.toString());

        // 用戶下单产生的下单记录Id压入死信队列，
        Integer id = save.getId();
        deadOrderPublisher.sendMsg(id);

    }

    public void updateUserOrderRecord(UserOrder userOrder) {
        try {
            if (userOrder != null) {
                //更新失效下单用户
                userOrder.setIsActive(0);
                userOrder.setUpdateTime(new Date());
                userOrderRepository.updateByPrimaryKeySelective(userOrder);
                //记录失效用户下单记录
                MqOrder mqOrder = new MqOrder();
                // 设置是失效时间
                mqOrder.setBusinessTime(new Date());

                //设置备注信息
                mqOrder.setMemo("更新失效当前用户下单记录Id，orderId = " + userOrder.getId());

                mqOrder.setOrderId(userOrder.getId());

                mqOrderJpaRepository.save(mqOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("用户下单支付超时-处理服务-更新用户下单记录的状态发生异常{}", e.getMessage());
        }

    }
}
