package com.example.server.Redis_抢红包案例.repository;


import com.example.server.Redis_抢红包案例.domain.RedRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedRecordRepository extends JpaRepository<RedRecord,Long> {
}
