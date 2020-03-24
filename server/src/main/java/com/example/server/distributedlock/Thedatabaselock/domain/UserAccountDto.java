package com.example.server.distributedlock.Thedatabaselock.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
public class UserAccountDto implements Serializable {

    private Integer userId;
    private Double amount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
