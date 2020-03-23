package com.example.server.Rabbit.RabbitCase_overtimeOrder.repository;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserOrderRepository extends JpaRepository<UserOrder,Long> {

    @Query(nativeQuery = true,value = "update user_order set is_active =:#{#userOrders.isActive} where id =:#{#userOrders.id}")
    @Modifying
    @Transactional
    Integer updateByPrimaryKeySelective(@Param("userOrders")UserOrder userOrder);

    @Transactional
    UserOrder  queryUserOrderByIdAndStatus(Integer orderId,Integer status);
}

