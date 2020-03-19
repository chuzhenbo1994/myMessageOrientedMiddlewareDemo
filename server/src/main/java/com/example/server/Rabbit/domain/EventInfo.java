package com.example.server.Rabbit.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EventInfo {

    private Integer id;
    private String module;
    private String name;
    private String desc;

    public EventInfo() {
    }

    public EventInfo(Integer id, String module, String name, String desc) {
        this.id = id;
        this.module = module;
        this.name = name;
        this.desc = desc;
    }
}
