package com.example.server.Redis_抢红包案例.repository;

import com.example.server.Redis_抢红包案例.domain.RedRecord;
import com.example.server.Redis_抢红包案例.domain.RedRobRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedRobRecordRepository extends JpaRepository<RedRobRecord,Long> {


}
