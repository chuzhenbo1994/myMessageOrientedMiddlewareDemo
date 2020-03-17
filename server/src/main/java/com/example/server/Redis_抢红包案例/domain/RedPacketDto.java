package com.example.server.Redis_抢红包案例.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.criteria.CriteriaBuilder;

@Data
@ToString
public class RedPacketDto {

    private Integer userId;
    private Integer total;
    private Integer amount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
