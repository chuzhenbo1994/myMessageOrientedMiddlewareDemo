package com.example.server.Rabbit.RabbitCase_loginLog.repository;

import com.example.server.Rabbit.RabbitCase_loginLog.domain.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLog, Long> {


}
