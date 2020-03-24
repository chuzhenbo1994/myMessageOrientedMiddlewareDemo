package com.example.server.Redis.Redis_用户注册案例.repository;

import com.example.server.Redis.Redis_用户注册案例.domain.UserReg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegRepository extends JpaRepository<UserReg,Long> {

    UserReg findUserRegByUserName(String userName);
}
