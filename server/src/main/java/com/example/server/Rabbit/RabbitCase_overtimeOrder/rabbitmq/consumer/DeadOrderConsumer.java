package com.example.server.Rabbit.RabbitCase_overtimeOrder.rabbitmq.consumer;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrder;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.repository.UserOrderRepository;
import com.example.server.Rabbit.RabbitCase_overtimeOrder.service.DeadOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadOrderConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DeadOrderService deadOrderService;
    @Autowired
    private UserOrderRepository userOrderRepository;

    @RabbitListener(queues = "${mq.consumer.order.real.queue.name}", containerFactory = "singleListenerContainerAuto")
    public void consumerMsg(@Payload Integer orderId) {
        logger.info("用户下单支付超时-处理服务-监听真正的队列 监听到的消息内容为 orderId：{}", orderId);
        try {
            UserOrder userOrder = userOrderRepository.queryUserOrderByIdAndStatus(orderId, 1);
            if (userOrder != null) {
                // 超时 没有支付此订单，失效该笔订单
                deadOrderService.updateUserOrderRecord(userOrder);
            }
        } catch (Exception e) {
            logger.info("用户下单支付超时-处理服务-监听真正的队列 监听到的消息发生了异常 orderId = {}", orderId);
        }
    }
}
