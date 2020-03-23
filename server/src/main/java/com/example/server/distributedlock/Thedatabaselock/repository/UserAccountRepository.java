package com.example.server.distributedlock.Thedatabaselock.repository;

import com.example.server.distributedlock.Thedatabaselock.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
}
