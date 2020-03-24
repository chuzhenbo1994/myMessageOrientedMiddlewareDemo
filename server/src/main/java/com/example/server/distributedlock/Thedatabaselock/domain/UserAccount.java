package com.example.server.distributedlock.Thedatabaselock.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.NavigableMap;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "version", columnDefinition = "int default 1")
    @Version()
    private Integer version;
    @Column(name = "is_active",columnDefinition = "tinyint default 1 ")
    private byte isActive;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public short getIsActive() {
        return isActive;
    }

    public void setIsActive(byte isActive) {
        this.isActive = isActive;
    }
}
