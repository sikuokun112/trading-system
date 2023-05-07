package com.trading.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "TRADING_HISTORY")
@NoArgsConstructor
public class TradingHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "crypto")
    private String crypto;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "price")
    private Double price;

    @Column(name = "balanceBefore")
    private Double balanceBefore;

    @Column(name = "balanceAfter")
    private Double balanceAfter;

    @Column(name = "action")
    private String action;

    @Column(name = "platform")
    private String platform;

    @Column(name = "tickerId")
    private Long tickerId;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

}
