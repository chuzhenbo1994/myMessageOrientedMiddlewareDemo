package com.example.server.distributedlock.Thedatabaselock.repository;


import com.example.server.distributedlock.Thedatabaselock.domain.UserAccountRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRecordRepository extends JpaRepository<UserAccountRecord,Long> {
}
