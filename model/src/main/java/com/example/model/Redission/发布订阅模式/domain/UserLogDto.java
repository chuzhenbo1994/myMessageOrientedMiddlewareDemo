package com.example.model.Redission.发布订阅模式.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class UserLogDto implements Serializable
{
    private String userName;
    private String passWord;
    private Integer userId;

    public UserLogDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserLogDto(String userName, String passWord, Integer userId) {
        this.userName = userName;
        this.passWord = passWord;
        this.userId = userId;
    }
}
