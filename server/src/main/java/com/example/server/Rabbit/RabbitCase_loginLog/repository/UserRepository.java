package com.example.server.Rabbit.RabbitCase_loginLog.repository;

import com.example.server.Rabbit.RabbitCase_loginLog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    public User findUserByUserNameAndPassword( String userName,String passWord);
}
