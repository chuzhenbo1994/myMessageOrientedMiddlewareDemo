package com.example.server.Rabbit.RabbitCase_overtimeOrder.repository;

import com.example.server.Rabbit.RabbitCase_overtimeOrder.domain.MqOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqOrderJpaRepository extends JpaRepository<MqOrder,Long> {
}
