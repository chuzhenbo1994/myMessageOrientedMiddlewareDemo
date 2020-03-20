package com.example.server.RabbitCase.repository;

import com.example.server.RabbitCase.domain.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysLogRepository extends JpaRepository<SysLog, Long> {


}
