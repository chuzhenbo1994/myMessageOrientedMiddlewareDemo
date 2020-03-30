package com.example.model.Redission.发布订阅模式.repository;


import com.example.model.Redission.发布订阅模式.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    public User findUserByUserNameAndPassword(String userName, String passWord);
}
