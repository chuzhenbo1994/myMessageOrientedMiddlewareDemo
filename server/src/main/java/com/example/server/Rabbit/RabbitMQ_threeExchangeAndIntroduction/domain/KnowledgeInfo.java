package com.example.server.Rabbit.RabbitMQ_threeExchangeAndIntroduction.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class KnowledgeInfo implements Serializable {
    private Integer id;
    private String mode;
    private String code;

    public KnowledgeInfo() {
    }

    public KnowledgeInfo(Integer id, String mode, String code) {
        this.id = id;
        this.mode = mode;
        this.code = code;
    }
}
