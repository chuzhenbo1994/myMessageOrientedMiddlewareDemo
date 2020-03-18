package com.example.server.Rabbit.RabbitMQ_B.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Person implements Serializable {
    private Integer id;
    private String name;
    private String userName;

    public Person() {
    }

    public Person(Integer id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }
}
