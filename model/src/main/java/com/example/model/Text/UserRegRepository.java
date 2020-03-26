package com.example.model.Text;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegRepository extends JpaRepository<UserReg,Long> {

    UserReg findUserRegByUserName(String userName);
}
