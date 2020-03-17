package com.example.server.Redis_抢红包案例.domain;

import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.NavigableMap;

@Entity
@Table(name = "red_rob_record")
@ToString
public class RedRobRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "red_packet")
    private String red_packet;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "rob_time")
    private Date robTime;
    @Column(name = "is_active")
    private Short isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRed_packet() {
        return red_packet;
    }

    public void setRed_packet(String red_packet) {
        this.red_packet = red_packet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRobTime() {
        return robTime;
    }

    public void setRobTime(Date robTime) {
        this.robTime = robTime;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }
}
