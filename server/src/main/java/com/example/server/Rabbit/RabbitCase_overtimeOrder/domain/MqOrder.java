package com.example.server.Rabbit.RabbitCase_overtimeOrder.domain;

import javax.persistence.*;
import java.util.Date;


@Table(name = "my_order")
@Entity
public class MqOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "business_time")
    private Date businessTime;
    @Column(name = "memo")
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
