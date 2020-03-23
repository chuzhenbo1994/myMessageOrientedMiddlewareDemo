package com.example.server.Rabbit.RabbitCase_overtimeOrder.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_order")
public class UserOrder {
    @Id
    @Column(name ="id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "is_active")
    private Integer isActive;
    @Column(name = "create_time")
    private Date creatTime;
    @Column(name = "update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
